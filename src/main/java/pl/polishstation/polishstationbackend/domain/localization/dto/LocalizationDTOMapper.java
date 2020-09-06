package pl.polishstation.polishstationbackend.domain.localization.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.domain.localization.Localization;

@Mapper
public interface LocalizationDTOMapper {
    LocalizationDTO convertIntoDTO(Localization localization);
    Localization convertIntoObject(LocalizationDTO localizationDTO);
    Localization convertIntoObject(LocalizationPostDTO localizationPostDTO);
}
