package pl.polishstation.polishstationbackend.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDTO {
    Integer placeNumber;
    PetrolStationDTO petrolStation;

    public PlaceDTO(PetrolStationDTO petrolStation) {
        this.petrolStation = petrolStation;
    }
}
