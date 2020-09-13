package pl.polishstation.polishstationbackend.domain.opinion.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainPostDTOMapper;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;

import java.time.LocalDateTime;

@Component
public class OpinionPostDTOMapper implements BasicDomainPostDTOMapper<Opinion, OpinionPostDTO> {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PetrolStationRepository petrolStationRepository;

    public Opinion convertIntoObject(OpinionPostDTO opinionPostDTO) {
        var appUser = appUserRepository.findById(opinionPostDTO.getUserId())
                .orElseThrow(EntityDoesNotExists::new);
        var petrolStation = petrolStationRepository.findById(opinionPostDTO.getPetrolStationId())
                .orElseThrow(EntityDoesNotExists::new);
        return Opinion.builder()
                .date(LocalDateTime.now())
                .mark(opinionPostDTO.getMark())
                .user(appUser)
                .petrolStation(petrolStation)
                .build();
    }
}
