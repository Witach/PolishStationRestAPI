package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStationStats;

import java.util.List;
import java.util.Set;

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
}

