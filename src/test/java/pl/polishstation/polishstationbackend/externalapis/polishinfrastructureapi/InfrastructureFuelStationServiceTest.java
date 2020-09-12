package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypesNames;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.utils.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class InfrastructureFuelStationServiceTest {


    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    InfrastructureFuelStationDTOMapper infrastructureFuelStationDTOMapper;

    @Autowired
    InfrastructureFuelStationFullConverter infrastructureFuelStationFullConverter;
    List<InfrastructureFuelStationDTO> infrastructureFuelStationDTO;

    @Autowired
    FuelTypeRepository fuelTypeRepository;

    @Autowired
    JsonWriter jsonWriter;
    @Value("${apis.infrastructure-fuel-station.file}")
    String pathToAFile;

    @Autowired
    InfrastructureFuelStationService infrastructureFuelStationService;

    @Autowired
    PetrolStationRepository petrolStationRepository;

    @BeforeEach
    void setUp() throws IOException {
        URL file = getClass().getClassLoader().getResource("./info/test/PLInfrastructure.json");
        infrastructureFuelStationDTO = objectMapper.readValue(new File(file.getPath()), new TypeReference<List<InfrastructureFuelStationDTO>>(){});
    }

    @Test
    void isAssignableToADTOClass() throws IOException {
        assertTrue(Objects.nonNull(infrastructureFuelStationDTO));
        assertTrue(Objects.nonNull(infrastructureFuelStationDTO.get(0)));
        assertTrue(Objects.nonNull(infrastructureFuelStationDTO.get(0).getDkn()));
        assertEquals(6020,infrastructureFuelStationDTO.get(0).getDkn());
    }

    @Test
    void shouldMapDtoIntoEntity() throws IOException {
        var petrolStation = infrastructureFuelStationDTOMapper.convertIntoObject(infrastructureFuelStationDTO.get(0));

        assertTrue(Objects.nonNull(petrolStation));
        assertTrue(Objects.nonNull(petrolStation.getDkn()));
        assertEquals(infrastructureFuelStationDTO.get(0).getDkn(), petrolStation.getDkn());
        assertTrue(Objects.nonNull(petrolStation.getLocalization()));
        assertEquals(infrastructureFuelStationDTO.get(0).getStreet(), petrolStation.getLocalization().getStreet());
    }

    @Test
    void shouldMapBidirectionalEntity() {
        var fullPetrolStation = infrastructureFuelStationFullConverter.convertIntoBidirectionalEntity(infrastructureFuelStationDTO.get(0));
        var diesel = fuelTypeRepository.findByName(FuelTypesNames.DIESEL).orElseThrow();
        var petrol = fuelTypeRepository.findByName(FuelTypesNames.PETROL).orElseThrow();
        assertTrue(Objects.nonNull(fullPetrolStation));
        assertTrue(Objects.nonNull(fullPetrolStation.getDkn()));
        assertTrue(Objects.nonNull(fullPetrolStation.getLocalization()));
        assertTrue(fullPetrolStation.getFuelTypes().contains(diesel));
        assertTrue(fullPetrolStation.getFuelTypes().contains(petrol));
    }

    @Test
    void shouldSaveToFile() {
        assertDoesNotThrow(() -> jsonWriter.writePojoToAFile(infrastructureFuelStationDTO,pathToAFile));
    }

    @Test
    void getFullFuelStationInfoAndPersists() {
        var diesel = fuelTypeRepository.findByName(FuelTypesNames.DIESEL).orElseThrow();
        var petrol = fuelTypeRepository.findByName(FuelTypesNames.PETROL).orElseThrow();
        var restTemplateMock = mock(RestTemplate.class);
        when(restTemplateMock.getForObject("", InfrastructureFuelStationDTO[].class))
                .thenReturn(infrastructureFuelStationDTO.toArray(InfrastructureFuelStationDTO[]::new));
        infrastructureFuelStationService.setRestTemplate(restTemplateMock);
        infrastructureFuelStationService.getFullFuelStationInfoAndPersists();
        var persistedPetrolStation = petrolStationRepository.findAll();
        persistedPetrolStation.forEach(System.out::println);
        var filteredPersistedPetroStations = persistedPetrolStation.stream()
                .filter(petrolStation -> petrolStation.getDkn() == 6020)
                .collect(Collectors.toList());
        assertEquals(4, filteredPersistedPetroStations.size());
        filteredPersistedPetroStations.forEach(petrolStation -> {
            assertNotNull(petrolStation.getLocalization());
            assertEquals(6020, petrolStation.getDkn());
            assertEquals("ul. Moniuszki ", petrolStation.getLocalization().getStreet());
            assertTrue(petrolStation.getFuelTypes().contains(petrol));
            assertTrue(petrolStation.getFuelTypes().contains(diesel));
        });
    }
}
