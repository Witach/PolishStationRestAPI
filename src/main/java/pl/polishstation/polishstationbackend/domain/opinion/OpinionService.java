package pl.polishstation.polishstationbackend.domain.opinion;

import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTO;

@Service
public class OpinionService extends BasicDomainService<Opinion, OpinionDTO, OpinionPostDTO> {
}
