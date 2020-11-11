package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationPostDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PetrolStationPostDTO {
    @NotBlank
    @Size(min = 3, max = 32)
    private String name;
    private Double dkn;
    @NotNull
    private LocalizationPostDTO localization;
    private List<String> fuelTypes;
    Boolean isWCFree;
    Boolean isWC;
    Boolean isRestaurant;
    Boolean isCompressor;
    Boolean isCarWash;
    Boolean isHotDogs;
    Boolean isSelfService;
}
