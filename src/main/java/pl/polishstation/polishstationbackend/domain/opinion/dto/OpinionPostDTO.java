package pl.polishstation.polishstationbackend.domain.opinion.dto;

import lombok.Data;


@Data
public class OpinionPostDTO {
    private Integer mark;
    private Long petrolStationId;
    private Long userId;
}
