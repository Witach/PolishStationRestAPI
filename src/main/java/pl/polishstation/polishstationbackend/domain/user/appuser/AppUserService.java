package pl.polishstation.polishstationbackend.domain.user.appuser;

import com.github.javafaker.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTOMapper;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRoleRepository;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.exception.UniqueDataArleadyExists;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static pl.polishstation.polishstationbackend.exception.ExcpetionFactory.uniqueDataExceptionOfClassResolver;

@Service
public class AppUserService {

    @Autowired
    AppUserRepository repository;
    @Autowired
    AppUserDTOMapper mapper;
    @Autowired
    AppUserRoleRepository appUserRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    BiFunction<String, String, UniqueDataArleadyExists> exceptionResolver = uniqueDataExceptionOfClassResolver(AppUser.class);

    public AppUserDTO addEntity(AppUserPostDTO dto) {
        if(repository.existsByEmail(dto.getEmail()))
            throw exceptionResolver.apply("email", dto.getEmail());
        if(repository.existsByUsername(dto.getUsername()))
            throw exceptionResolver.apply("username", dto.getEmail());
        var newAppUser = mapper.convertIntoObject(dto);
        newAppUser.setIsVerified(false);
        attachDefaultRoleToUser(newAppUser);
        encodeUsersPassword(newAppUser);
        return mapper.convertIntoDTO(
                repository.save(newAppUser)
        );
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

    public void deleteEntity(Long entity) {
        repository.deleteById(entity);
    }

    public void updateEntity(AppUserDTO dto, Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExists();
        var appUser = mapper.convertIntoOject(dto);
        repository.save(appUser);
    }

    public AppUserDTO getEntityById(Long id) {
        return repository.findById(id)
                .map(mapper::convertIntoDTO)
                .orElseThrow(EntityDoesNotExists::new);
    }

    public List<AppUserDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(mapper::convertIntoDTO)
                .collect(Collectors.toList());
    }
}
