package pl.polishstation.polishstationbackend.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuelPriceStats {
    LocalDate date;
    Double price;

    static public FuelPriceStats  fuelPriceStats(FuelPrice fuelPrice) {
        return FuelPriceStats.builder()
                .date(fuelPrice.getDate().toLocalDate())
                .price(fuelPrice.getPrice())
                .build();
    }
}
