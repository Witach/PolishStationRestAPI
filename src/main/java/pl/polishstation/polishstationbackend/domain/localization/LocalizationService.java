package pl.polishstation.polishstationbackend.domain.localization;

import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationPostDTO;

@Service
public class LocalizationService extends BasicDomainService<Localization, LocalizationDTO, LocalizationPostDTO> {
}
