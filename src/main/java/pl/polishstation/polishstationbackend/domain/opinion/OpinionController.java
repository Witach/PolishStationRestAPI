package pl.polishstation.polishstationbackend.domain.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTO;

import java.util.List;

@Loggable
@CrossOrigin
@RestController
@RequestMapping("/opinion")
public class OpinionController extends BasicDomainController<Opinion, OpinionDTO, OpinionPostDTO> {

    @Autowired
    OpinionService opinionService;

    @GetMapping("/user/{emial}")
    public List<OpinionDTO> opinionDTOList(@PathVariable String emial) {
        return opinionService.getUsersOpinion(emial);
    }
}
