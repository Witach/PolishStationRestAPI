package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FuelPricePostDTO {
    private Double price;
    private LocalDateTime date;
    private Long appUserId;
    private String fuelType;
    private Long PetrolStationId;
}
