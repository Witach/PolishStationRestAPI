package pl.polishstation.polishstationbackend.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuelPriceStatsDTO {
    Map<String, List<FuelPriceStats>> fuelPriceStats;
    Map<String, DetailsStats> detailsStatsMap;
}
