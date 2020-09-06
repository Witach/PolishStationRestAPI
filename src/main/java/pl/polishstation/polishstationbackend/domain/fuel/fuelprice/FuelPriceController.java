package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTO;

import static  org.springframework.http.HttpStatus.*;

import java.util.List;

@Loggable
@RestController
@RequestMapping("/fuel-price")
public class FuelPriceController {

    @Autowired
    FuelPriceService service;

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
    public void update(@RequestBody FuelPricePostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public FuelPriceDTO post(@RequestBody FuelPricePostDTO dto) {
        return service.addEntity(dto);
    }

}
