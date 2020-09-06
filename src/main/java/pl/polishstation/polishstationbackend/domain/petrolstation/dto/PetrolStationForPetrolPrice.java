package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStationStats;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetrolStationForPetrolPrice {
    private Long id;

    private String name;

    private Double dkn;

    PetrolStationStats petrolStationStats;

    private List<String> fuelTypes;
}
