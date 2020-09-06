package pl.polishstation.polishstationbackend.domain.localization.dto;

import lombok.Data;

@Data
public class LocalizationPostDTO {
    private String name;
    private String street;
    private String number;
    private String postalCode;
    private String province;
}
