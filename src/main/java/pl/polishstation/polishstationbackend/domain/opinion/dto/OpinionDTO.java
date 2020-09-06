package pl.polishstation.polishstationbackend.domain.opinion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpinionDTO {
    private Long id;
    private LocalDateTime date;
    private Integer mark;
    private PetrolStation petrolStation;
    private AppUser user;
}
