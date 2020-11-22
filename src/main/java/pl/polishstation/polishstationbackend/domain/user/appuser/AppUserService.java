package pl.polishstation.polishstationbackend.domain.user.appuser;

import com.github.javafaker.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsImpl;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserStatsDTO;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRoleRepository;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.exception.UniqueDataArleadyExists;

import java.security.Security;
import java.util.Optional;
import java.util.function.BiFunction;

import static pl.polishstation.polishstationbackend.exception.ExcpetionFactory.uniqueDataExceptionOfClassResolver;

@Service
public class  AppUserService extends BasicDomainService<AppUser, AppUserDTO, AppUserPostDTO> {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppUserRoleRepository appUserRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    BiFunction<String, String, UniqueDataArleadyExists> exceptionResolver = uniqueDataExceptionOfClassResolver(AppUser.class);

    public AppUserDTO addEntity(AppUserPostDTO dto) {
        var newAppUser = prepareNewUser(dto);
        return mapper.convertIntoDTO(
                repository.save(newAppUser)
        );
    }

    public AppUserDTO persistsAppUser(AppUser appUser) {
        return mapper.convertIntoDTO(
                repository.save(appUser)
        );
    }

    public AppUser prepareNewUser(AppUserPostDTO dto) {
        if(appUserRepository.existsByEmail(dto.getEmail()))
            throw exceptionResolver.apply("email", dto.getEmail());
        if(appUserRepository.existsByUsername(dto.getUsername()))
            throw exceptionResolver.apply("username", dto.getEmail());
        var newAppUser = postDTOMapper.convertIntoObject(dto);
        newAppUser.setIsVerified(false);
        attachDefaultRoleToUser(newAppUser);
        encodeUsersPassword(newAppUser);
        return newAppUser;
    }

    private void encodeUsersPassword(AppUser newAppUser) {
        var password = newAppUser.getPassword();
        var encoded = passwordEncoder.encode(password);
        newAppUser.setPassword(encoded);
    }

    private void attachDefaultRoleToUser(AppUser newAppUser) {
        var role = appUserRoleRepository.getDefaultUserRole().orElseThrow();
        newAppUser.addRole(role);
    }

    @Override
    public void updateEntity(AppUserPostDTO appUserPostDTO, Long id) {
        var appUser = repository.findById(id).orElseThrow(EntityDoesNotExists::new);
        Optional.ofNullable(appUserPostDTO.getPassword()).ifPresent(appUser::setPassword);
        Optional.ofNullable(appUserPostDTO.getUsername()).ifPresent(appUser::setUsername);
        repository.save(appUser);
    }

    @Transactional
    public AppUserStatsDTO getUserStats(Long id) {
        var appUser = repository.findById(id).orElseThrow(EntityDoesNotExists::new);
        var amountOfAddedFuelTypes = (long) appUser.getFuelPrices().size();
        var amountOfOpinions = appUser.getOpinion().size();
        var amountOfCreatedStations = Optional.ofNullable(appUser.getAmountOfCreatedStations()).orElse(0L);
        var amountOfEdited = Optional.ofNullable(appUser.getAmountOfEditedInformations()).orElse(0L);
        return AppUserStatsDTO.builder()
                .amountOfAddedFuelTypes(amountOfAddedFuelTypes)
                .amountOfCreatedStations(amountOfCreatedStations)
                .amountOfEditedInformations(amountOfEdited)
                .amountOfOpinions((long)amountOfOpinions)
                .build();
    }

    public void addCreationToUser() {
        var userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var appUser = appUserRepository.findAppUserByEmailEquals(userDetails.getUsername()).orElseThrow(EntityDoesNotExists::new);
        Optional.ofNullable(appUser.getAmountOfCreatedStations()).ifPresentOrElse(
                number -> appUser.setAmountOfCreatedStations(number + 1L),
                () -> appUser.setAmountOfCreatedStations(1L)
        );
        repository.save(appUser);
    }

    public void addEditingPoint() {
        var userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var appUser = appUserRepository.findAppUserByEmailEquals(userDetails.getUsername()).orElseThrow(EntityDoesNotExists::new);
        Optional.ofNullable(appUser.getAmountOfEditedInformations()).ifPresentOrElse(
                number -> appUser.setAmountOfEditedInformations(number + 1L),
                () -> appUser.setAmountOfEditedInformations(1L)
        );
        repository.save(appUser);
    }
}
