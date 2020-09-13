package pl.polishstation.polishstationbackend.dup;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Dup {
    @NotBlank
    @Size(min = 1, max = 3)
    String mame;
}
