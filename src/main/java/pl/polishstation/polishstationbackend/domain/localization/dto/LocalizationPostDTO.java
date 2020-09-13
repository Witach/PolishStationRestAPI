package pl.polishstation.polishstationbackend.domain.localization.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LocalizationPostDTO {
    @Size(min = 3, max= 64)
    private String name;
    @Size(min = 3, max = 64)
    private String street;
    @Size(min = 1, max = 8)
    private String number;
    @Pattern(regexp = "^[0-9][0-9]-[0-9][0-9][0-9]$", flags = Pattern.Flag.UNICODE_CASE)
    private String postalCode;
    @Size(min = 3, max= 64)
    private String province;
}
