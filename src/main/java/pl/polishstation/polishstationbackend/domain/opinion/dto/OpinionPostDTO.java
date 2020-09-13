package pl.polishstation.polishstationbackend.domain.opinion.dto;

import lombok.Data;
import pl.polishstation.polishstationbackend.auth.validation.SessionOwner;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
public class OpinionPostDTO {
    @Min(0)
    @Max(5)
    private Integer mark;
    @NotNull
    private Long petrolStationId;
    @NotNull
    @SessionOwner
    private Long userId;
}
