package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastFuelPriceDTO {
    private Long id;
    Double price;
    String fuelType;
    LocalDateTime date;
}
