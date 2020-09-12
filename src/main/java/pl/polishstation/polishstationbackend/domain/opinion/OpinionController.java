package pl.polishstation.polishstationbackend.domain.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTO;

import static  org.springframework.http.HttpStatus.*;

import java.util.List;

@Loggable
@RestController
@RequestMapping("/opinion")
public class OpinionController extends BasicDomainController<Opinion, OpinionDTO, OpinionPostDTO> {
}
