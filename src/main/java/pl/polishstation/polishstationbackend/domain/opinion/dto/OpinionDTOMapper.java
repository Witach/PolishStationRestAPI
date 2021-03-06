package pl.polishstation.polishstationbackend.domain.opinion.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainDTOMapper;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;

@Mapper
public interface OpinionDTOMapper extends BasicDomainDTOMapper<Opinion, OpinionDTO> {
    @Mappings({
            @Mapping(source = "petrolStation.id", target = "petrolStationId"),
            @Mapping(source = "user.id", target = "userId")
    })
    OpinionDTO convertIntoDTO(Opinion opinion);
    Opinion convertIntoObject(OpinionDTO opinionDTO);
}
