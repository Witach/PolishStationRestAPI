package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeService;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationDTO;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationFullConverter;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationService;
import pl.polishstation.polishstationbackend.utils.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InfrastructureFuelStationServiceUnitTest {
    @Mock
    RestTemplate restTemplate;
    @Mock
    private LocalizationRepository localizationRepository;
    @Mock
    private PetrolStationRepository petrolStationRepository;
    @Mock
    private InfrastructureFuelStationFullConverter infrastructureFuelStationFullConverter;
    @Mock
    private FuelTypeService fuelTypeService;
    @Mock
    private JsonWriter jsonWriter;
    @Captor
    ArgumentCaptor<Object> argCaptor;

    List<InfrastructureFuelStationDTO> infrastructureFuelStationDTOS;

    InfrastructureFuelStationService infrastructureFuelStationService;


    @BeforeEach
    void setUp() throws IOException {
        var objectMapper = new ObjectMapper();
        infrastructureFuelStationService = new InfrastructureFuelStationService(localizationRepository, petrolStationRepository, infrastructureFuelStationFullConverter, fuelTypeService, jsonWriter);
        infrastructureFuelStationService.setRestTemplate(restTemplate);
        URL file = getClass().getClassLoader().getResource("./info/test/PLInfrastructure.json");
        infrastructureFuelStationDTOS = objectMapper.readValue(new File(file.getPath()), new TypeReference<>(){});
    }

    @Test
    void getFullFuelStationInfoAndSaveIntoFile() throws IOException {
        doNothing().when(jsonWriter).writePojoToAFile(argCaptor.capture(),any());
        when(restTemplate.getForObject("", InfrastructureFuelStationDTO[].class ))
                .thenReturn(infrastructureFuelStationDTOS.toArray(InfrastructureFuelStationDTO[]::new));
        infrastructureFuelStationService.getFullFuelStationInfoAndSaveIntoFile();
        assertTrue(Objects.nonNull(argCaptor.getValue()));
        ObjectMapper objectMapper1 = new ObjectMapper();
        URL file = getClass().getClassLoader().getResource("./info/test/PLInfrastructure.json");
        var dtoResponse = objectMapper1.readValue(new File(file.getPath()), new TypeReference<List<InfrastructureFuelStationDTO>>(){});
        assertEquals(6020, dtoResponse.get(0).getDkn());
    }
}
