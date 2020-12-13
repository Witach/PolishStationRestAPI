package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStationStats;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetrolStationDTO {
    private Long id;
    private String name;
    private Double dkn;
    private LocalizationDTO localization;
    private PetrolStationStats petrolStationStats;
    private List<LastFuelPriceDTO> fuelPriceDTO;
    private List<String> fuelTypes;
    private Double distance;
    private Boolean isLovedByUser;
}

