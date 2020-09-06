package pl.polishstation.polishstationbackend.domain.opinion.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;

@Mapper
public interface OpinionDTOMapper {
    OpinionDTO convertIntoDTO(Opinion opinion);
    Opinion convertIntoObject(OpinionDTO opinionDTO);
}
