package pl.polishstation.polishstationbackend.domain.localization.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainPostDTOMapper;
import pl.polishstation.polishstationbackend.domain.localization.Localization;

@Mapper
public interface LocalizationPostDTOMapper extends BasicDomainPostDTOMapper<Localization, LocalizationPostDTO> {
    Localization convertIntoObject(LocalizationPostDTO localizationPostDTO);
}
