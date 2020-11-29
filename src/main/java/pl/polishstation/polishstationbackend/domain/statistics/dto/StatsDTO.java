package pl.polishstation.polishstationbackend.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsDTO {
    LocalDateTime day;
    List<PlaceDTO> facilitiesRank;
    List<PlaceDTO> opinionRank;
    Map<String,List<PlaceDTO>> fuelTypePriceRank;

}
