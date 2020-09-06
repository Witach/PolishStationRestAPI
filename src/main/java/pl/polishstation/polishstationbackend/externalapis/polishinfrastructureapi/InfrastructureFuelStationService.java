package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeService;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.exception.NoDataReceivedException;
import pl.polishstation.polishstationbackend.utils.JsonWriter;

import java.util.List;
import java.util.Optional;


@Service
public class InfrastructureFuelStationService {

    public static String JSON_FILE_PATH;

    public static String URI;

    RestTemplate restTemplate;

    private LocalizationRepository localizationRepository;

    private PetrolStationRepository petrolStationRepository;

    private InfrastructureFuelStationFullConverter infrastructureFuelStationFullConverter;

    private FuelTypeService fuelTypeService;

    public JsonWriter jsonWriter;

    @Autowired
    public InfrastructureFuelStationService(LocalizationRepository localizationRepository, PetrolStationRepository petrolStationRepository, InfrastructureFuelStationFullConverter infrastructureFuelStationFullConverter, FuelTypeService fuelTypeService, JsonWriter jsonWriter) {
        this.localizationRepository = localizationRepository;
        this.petrolStationRepository = petrolStationRepository;
        this.infrastructureFuelStationFullConverter = infrastructureFuelStationFullConverter;
        this.fuelTypeService = fuelTypeService;
        this.jsonWriter = jsonWriter;
    }

    public InfrastructureFuelStationService() {
        restTemplate = new RestTemplateBuilder()
                .rootUri(URI)
                .build();
    }

    void getFullFuelStationInfoAndSaveIntoFile() {
        var data = getFullFuelStationsInfo();
        jsonWriter.writePojoToAFile(data, JSON_FILE_PATH);
    }

    void getFullFuelStationInfoAndPersists() {
        var data = getFullFuelStationsInfo();
        var petrolStations = infrastructureFuelStationFullConverter.convertIntoBidirectionalEntity(data);
        petrolStationRepository.saveAll(petrolStations);
    }

    List<InfrastructureFuelStationDTO> getFullFuelStationsInfo() {
        var optResponse = Optional.ofNullable(
                restTemplate.getForObject("", InfrastructureFuelStationDTO[].class)
        );
        var response = optResponse.orElseThrow(NoDataReceivedException::new);
        return List.of(response);
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
