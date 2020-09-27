package pl.polishstation.polishstationbackend.domain.user.appuser;

import com.github.javafaker.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRoleRepository;
import pl.polishstation.polishstationbackend.exception.UniqueDataArleadyExists;

import java.util.function.BiFunction;

import static pl.polishstation.polishstationbackend.exception.ExcpetionFactory.uniqueDataExceptionOfClassResolver;

@Service
public class AppUserService extends BasicDomainService<AppUser, AppUserDTO, AppUserPostDTO> {

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
}
