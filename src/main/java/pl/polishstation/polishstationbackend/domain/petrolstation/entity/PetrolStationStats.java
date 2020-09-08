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
    Integer avgOpinion;
    Long amountOfOpinion;
    Double avgPrice;

    @Override
    public PetrolStationStats cloneEntity() {
        return PetrolStationStats.builder()
                .amountOfOpinion(amountOfOpinion)
                .avgOpinion(avgOpinion)
                .build();
    }
}
