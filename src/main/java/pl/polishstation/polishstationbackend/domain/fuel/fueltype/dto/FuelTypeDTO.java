package pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class FuelTypeDTO {
    @Size(min = 1, max = 16)
    private String name;
}
