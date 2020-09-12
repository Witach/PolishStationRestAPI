package pl.polishstation.polishstationbackend.domain.localization.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.apiutils.BasicDomainDTOMapper;
import pl.polishstation.polishstationbackend.apiutils.BasicDomainPostDTOMapper;
import pl.polishstation.polishstationbackend.domain.localization.Localization;

@Mapper
public interface LocalizationDTOMapper extends BasicDomainDTOMapper<Localization, LocalizationDTO> {
    LocalizationDTO convertIntoDTO(Localization localization);
    Localization convertIntoObject(LocalizationDTO localizationDTO);
}
