package pl.polishstation.polishstationbackend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsImpl;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserService;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class IsLovedAspect {

    @Autowired
    AppUserService appUserService;

    @AfterReturning(pointcut = "@annotation(IsLoved)", returning = "retValue")
    void attachIsLovedField(Object retValue) {
        var appUserDetialsImpl =  (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(appUserDetialsImpl != null && appUserDetialsImpl.getUsername() != null) {
            var list = (List<PetrolStationDTO>)retValue;
            var lovedIds = appUserService.getLovedPetrolStaitonsOfUser(appUserDetialsImpl.getUsername())
                    .stream()
                    .map(PetrolStationDTO::getId)
                    .collect(Collectors.toList());
            list.forEach(petrolStationDTO -> petrolStationDTO.setIsLovedByUser(lovedIds.contains(petrolStationDTO.getId())));
        }

    }

    @AfterReturning(pointcut = "@annotation(IsOneLoved)", returning = "retValue")
    void attachIsLovedFieldToOne(Object retValue) {
        var appUserDetialsImpl =  (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(appUserDetialsImpl != null && appUserDetialsImpl.getUsername() != null) {
            var petrolStationDTO = (PetrolStationDTO)retValue;
            var lovedIds = appUserService.getLovedPetrolStaitonsOfUser(appUserDetialsImpl.getUsername())
                    .stream()
                    .map(PetrolStationDTO::getId)
                    .collect(Collectors.toList());
            petrolStationDTO.setIsLovedByUser(lovedIds.contains(petrolStationDTO.getId()));
        }

    }
}
