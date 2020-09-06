package pl.polishstation.polishstationbackend.domain.user.appuserrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.dto.AppUserRoleMapper;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.dto.AppUserRoleDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserRoleService{

    @Autowired
    private AppUserRoleRepository repository;

    @Autowired
    private AppUserRoleMapper mapper;

    public AppUserRoleDTO addEntity(AppUserRoleDTO dto) {
        AppUserRole newUserRole = mapper.convertIntoObject(dto);
        return mapper.convertIntoDTO(
                repository.save(newUserRole)
        );
    }

    public void deleteEntity(String name) {
        repository.deleteAppUserRoleByNameEquals(name);
    }

    public List<String> getAllEntities() {
        return repository.findAll().stream()
                .map(AppUserRole::getName)
                .collect(Collectors.toList());
    }
}
