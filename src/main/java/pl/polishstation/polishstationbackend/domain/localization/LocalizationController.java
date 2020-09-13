package pl.polishstation.polishstationbackend.domain.localization;

import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationPostDTO;

@Loggable
@RestController
@RequestMapping("/localization")
public class LocalizationController extends BasicDomainController<Localization, LocalizationDTO, LocalizationPostDTO> {
}
