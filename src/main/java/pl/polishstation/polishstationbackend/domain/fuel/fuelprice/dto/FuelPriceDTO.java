package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationForPetrolPrice;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelPriceDTO {
    private Long id;
    Double price;
    LocalDateTime localeDataTime;
    AppUserDTO appUserDTO;
    String fuelType;
    PetrolStationForPetrolPrice petrolStation;
    private Boolean verified = false;
}
