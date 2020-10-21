package pl.polishstation.polishstationbackend.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class JsonWriterReader {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ObjectMapper objectMapper;

    public JsonWriterReader() {
    }

    public  void writePojoToAFile(Object dto, String jsonFilePath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File jsonFile = new File(classLoader.getResource(jsonFilePath).getFile());
            String jsonString = objectMapper.writeValueAsString(dto);
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonString);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e){
            log.error("Error durring writting a json file");
            e.printStackTrace();
        }
    }

    public <DTO> DTO loadPojoFromAFile(String jsonFilePath, TypeReference<DTO> dtoClass) {
        DTO res = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File jsonFile = new File(classLoader.getResource(jsonFilePath).getFile());
            var fis = new FileInputStream(jsonFile);
            byte[] data = new byte[(int) jsonFile.length()];
            fis.read(data);
            fis.close();

            String jsonStr = new String(data, "UTF-8");
            res = objectMapper.readValue(jsonStr, dtoClass);

        } catch (IOException e){
            log.error("Error durring writting a json file");
            e.printStackTrace();
        }
        return res;
    }
}
