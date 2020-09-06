package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationPostDTO;

import java.util.List;

@Data
public class PetrolStationPostDTO {
    private String name;
    private Double dkn;
    private LocalizationPostDTO localization;
    private List<String> fuelTypes;
}
