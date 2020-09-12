package pl.polishstation.polishstationbackend.apiutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeDTO;

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
    public DomainDTO create(@RequestBody DomainDTO dto) {
        return service.addEntity(dto);
    }
}
