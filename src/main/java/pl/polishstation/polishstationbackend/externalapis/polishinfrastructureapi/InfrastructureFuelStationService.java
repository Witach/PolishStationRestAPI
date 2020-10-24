package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeService;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.AddressFormater;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.exception.NoDataReceivedException;
import pl.polishstation.polishstationbackend.utils.JsonWriterReader;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
public class InfrastructureFuelStationService {

    public static String JSON_FILE_PATH;

    public static String URI;

    @Value("${apis.infrastructure-fuel-station.mock.file}")
    public  String fuelStationsFile;

    @Value("${apis.infrastructure-fuel-station.mock.localization}")
    public  String localizationForMock;

    RestTemplate restTemplate;

    private LocalizationRepository localizationRepository;

    private PetrolStationRepository petrolStationRepository;

    private InfrastructureFuelStationFullConverter infrastructureFuelStationFullConverter;

    private FuelTypeService fuelTypeService;

    public JsonWriterReader jsonWriter;

    @Autowired
    public AddressFormater addressFormater;

    @Autowired
    public InfrastructureFuelStationService(LocalizationRepository localizationRepository, PetrolStationRepository petrolStationRepository, InfrastructureFuelStationFullConverter infrastructureFuelStationFullConverter, FuelTypeService fuelTypeService, JsonWriterReader jsonWriter) {
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

    public void getFullFuelStationInfoAndSaveIntoFile() {
        var data = getFullFuelStationsInfo();
        jsonWriter.writePojoToAFile(data, JSON_FILE_PATH);
    }

    public void getFullFuelStationInfoAndSaveIntoFile(String localization) {
        var data = getFullFuelStationsInfo();
        var result = data.stream()
                .filter( station -> filterLocalizationOfPetrolStaitonsFun(station, localization))
                .collect(Collectors.toList());

        jsonWriter.writePojoToAFile(result, JSON_FILE_PATH);
    }

    public boolean filterLocalizationOfPetrolStaitonsFun(InfrastructureFuelStationDTO infrastructureFuelStationDTO, String localization) {
        List<Supplier<String>> lsitOfGetters = List.of(
                infrastructureFuelStationDTO::getName,
                infrastructureFuelStationDTO::getTown,
                infrastructureFuelStationDTO::getAddress,
                infrastructureFuelStationDTO::getRegionName,
                infrastructureFuelStationDTO::getLocalization,
                infrastructureFuelStationDTO::getPostOfficeName,
                infrastructureFuelStationDTO::getPostOfficeAddress
        );
        return lsitOfGetters.stream()
                .map(Supplier::get)
                .anyMatch(value -> value.contains(localization));
    }

    public void loadFuelStationsFromFileAndPersist(String path) {
        List<InfrastructureFuelStationDTO> list = jsonWriter.loadPojoFromAFile(path,new TypeReference<List<InfrastructureFuelStationDTO>>(){});
        var petrolStations = infrastructureFuelStationFullConverter.convertIntoBidirectionalEntity(list);
        petrolStations.forEach(petrolStation ->  {
                    var formattedString = addressFormater.formatAddressStringFromLocalization(petrolStation.getLocalization());
                    petrolStation.getLocalization().setFormattedAddress(formattedString);
                });
        petrolStationRepository.saveAll(petrolStations);
    }

    public void getFullFuelStationInfoAndPersists() {
        var data = getFullFuelStationsInfo();
        var petrolStations = infrastructureFuelStationFullConverter.convertIntoBidirectionalEntity(data);
        petrolStationRepository.saveAll(petrolStations);
    }

    List<InfrastructureFuelStationDTO> getFullFuelStationsInfo() {
        var optResponse = Optional.ofNullable(
                restTemplate.getForObject("https://api.ure.gov.pl/api/InfrastructureFuelStation", InfrastructureFuelStationDTO[].class)
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
