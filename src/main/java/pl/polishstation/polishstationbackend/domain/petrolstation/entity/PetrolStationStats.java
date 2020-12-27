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
    Double avgOpinion = 0D;
    Long amountOfOpinion = 0L;
    Double avgPrice = 0D;
    Boolean isHotDogs = false;
    Boolean isWCFree = false;
    Boolean isWC = false;
    Boolean isRestaurant = false;
    Boolean isCompressor = false;
    Boolean isCarWash = false;
    Boolean isSelfService = false;

    @Override
    public PetrolStationStats cloneEntity() {
        return PetrolStationStats.builder()
                .amountOfOpinion(amountOfOpinion)
                .avgOpinion(avgOpinion)
                .build();
    }

    public static class PetrolStationStatsBuilder {
        private Double avgOpinion = 0D;
        private Long amountOfOpinion = 0L;
        private Double avgPrice = 0D;
    }
}
