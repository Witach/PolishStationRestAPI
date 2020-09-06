package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeDTO;

import static  org.springframework.http.HttpStatus.*;

import java.util.List;

@Loggable
@RestController
@RequestMapping("/fuel-type")
public class FuelTypeController {

    @Autowired
    FuelTypeService service;

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
    public FuelTypeDTO create(@RequestBody FuelTypeDTO dto) {
        return service.addEntity(dto);
    }

}
