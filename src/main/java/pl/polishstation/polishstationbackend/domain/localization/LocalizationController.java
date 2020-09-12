package pl.polishstation.polishstationbackend.domain.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationPostDTO;

import static  org.springframework.http.HttpStatus.*;

import java.util.List;

@Loggable
@RestController
@RequestMapping("/localization")
public class LocalizationController extends BasicDomainController<Localization, LocalizationDTO, LocalizationPostDTO> {
}
