package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto;

import lombok.Data;
import pl.polishstation.polishstationbackend.auth.validation.SessionOwner;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.validation.Precision;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class FuelPricePostDTO {
    @Precision
    @Positive
    private Double price;

    @PastOrPresent
    private LocalDateTime date;

    @SessionOwner
    private Long appUserId;

    private String fuelType;
    private Long PetrolStationId;
}
