package pl.polishstation.polishstationbackend.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.stream.JsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationDTO;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationService;
import pl.polishstation.polishstationbackend.utils.JsonWriterReader;

import java.util.List;
import java.util.stream.Collectors;

import static pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationService.JSON_FILE_PATH;

@Profile("fetch")
@Component
public class FetchPetrolInfoIntoFIle implements CommandLineRunner {

    @Autowired
    InfrastructureFuelStationService infrastructureFuelStationService;

    @Value("${apis.infrastructure-fuel-station.mock.localization}")
    String localizationOfMock;

    @Autowired
    JsonWriterReader jsonWriter;

    @Override
    public void run(String... args) throws Exception {
        List<InfrastructureFuelStationDTO> list = jsonWriter.loadPojoFromAFile("./info/InfrastructureFuelStation.json", new TypeReference<List<InfrastructureFuelStationDTO>>(){});
        var result = list.stream()
                .filter( station -> infrastructureFuelStationService.filterLocalizationOfPetrolStaitonsFun(station, "Łódź"))
                .collect(Collectors.toList());

        jsonWriter.writePojoToAFile(result, "./info/LodzStations.json");
    }
}
