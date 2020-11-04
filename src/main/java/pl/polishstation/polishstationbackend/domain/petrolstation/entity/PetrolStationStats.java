package pl.polishstation.polishstationbackend.domain.petrolstation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.utils.CloneableEntity;

import javax.persistence.Embeddable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetrolStationStats implements CloneableEntity<PetrolStationStats> {
    Double avgOpinion;
    Long amountOfOpinion;
    Double avgPrice;
    Boolean isHotDogs = false;
    Boolean isWCFree = false;
    Boolean isWC = false;
    Boolean isRestaurant = false;
    Boolean isCompressor = false;
    Boolean isCarWash = false;

    @Override
    public PetrolStationStats cloneEntity() {
        return PetrolStationStats.builder()
                .amountOfOpinion(amountOfOpinion)
                .avgOpinion(avgOpinion)
                .build();
    }
}
