package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTO;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Loggable
@RestController
@CrossOrigin
@RequestMapping("/fuel-price")
public class FuelPriceController  {

    @Autowired
    protected FuelPriceService service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public FuelPriceDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<FuelPriceDTO> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@Valid @RequestBody FuelPricePostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public ResMessage post(@Valid @RequestBody FuelPricePostDTO dto) {
        return service.addEntityClosure(dto);
    }
}
