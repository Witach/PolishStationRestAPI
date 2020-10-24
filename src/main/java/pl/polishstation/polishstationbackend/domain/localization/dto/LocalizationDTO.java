package pl.polishstation.polishstationbackend.domain.localization.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalizationDTO {
    private String name;
    private String street;
    private String number;
    private String postalCode;
    private String province;
    String formattedAddress;

    private String lat;
    @JsonProperty("long")
    private String _long;
}
