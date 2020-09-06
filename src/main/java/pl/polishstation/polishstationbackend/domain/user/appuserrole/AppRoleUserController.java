package pl.polishstation.polishstationbackend.domain.user.appuserrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.dto.AppUserRoleDTO;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@Loggable
@RestController
@RequestMapping("/app-user-role")
public class AppRoleUserController {

    @Autowired
    AppUserRoleService service;

    @ResponseStatus(OK)
    @GetMapping
    public List<String> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        service.deleteEntity(name);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public AppUserRoleDTO create(@RequestBody AppUserRoleDTO dto) {
        return service.addEntity(dto);
    }

}
