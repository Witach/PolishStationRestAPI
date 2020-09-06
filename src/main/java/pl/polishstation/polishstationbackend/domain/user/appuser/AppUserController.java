package pl.polishstation.polishstationbackend.domain.user.appuser;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@Loggable
@RestController
@RequestMapping("/app-user")
public class AppUserController {

    @Autowired
    AppUserService service;

    @ResponseStatus(OK)
    @GetMapping
    public List<AppUserDTO> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@RequestBody AppUserDTO entity, @PathVariable Long id) {
        service.updateEntity(entity, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public AppUserDTO create(@RequestBody AppUserPostDTO entity) {
        return service.addEntity(entity);
    }

}
