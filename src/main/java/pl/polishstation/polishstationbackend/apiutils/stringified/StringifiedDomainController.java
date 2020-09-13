package pl.polishstation.polishstationbackend.apiutils.stringified;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

public class StringifiedDomainController<Domain, DomainDTO> {

    @Autowired
    StringifiedDomainService<Domain, DomainDTO> service;

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
    public DomainDTO create(@Valid @RequestBody DomainDTO dto) {
        return service.addEntity(dto);
    }
}
