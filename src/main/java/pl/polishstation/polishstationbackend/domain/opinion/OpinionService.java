package pl.polishstation.polishstationbackend.domain.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpinionService extends BasicDomainService<Opinion, OpinionDTO, OpinionPostDTO> {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OpinionRpository opinionRpository;

    public List<OpinionDTO> getUsersOpinion(String email) {
        var user = appUserRepository.findAppUserByEmailEquals(email).orElseThrow();
        return opinionRpository.findAllByUser(user).stream().map(mapper::convertIntoDTO).collect(Collectors.toList());
    }
}
