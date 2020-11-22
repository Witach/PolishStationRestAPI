package pl.polishstation.polishstationbackend.domain.user.appuser.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserStatsDTO {
    Long amountOfCreatedStations;
    Long amountOfPoints;
    Long amountOfAddedFuelTypes;
    Long amountOfOpinions;
    Long amountOfEditedInformations;
}
