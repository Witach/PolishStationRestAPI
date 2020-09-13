package pl.polishstation.polishstationbackend.domain.opinion;

import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTO;

@Loggable
@RestController
@RequestMapping("/opinion")
public class OpinionController extends BasicDomainController<Opinion, OpinionDTO, OpinionPostDTO> {
}
