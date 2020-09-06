package pl.polishstation.polishstationbackend.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeService;

@Component
public class FuelTypesPersister implements CommandLineRunner {

    @Autowired
    FuelTypeService fuelTypeService;

    @Override
    public void run(String... args) {
        fuelTypeService.persistsDefaultFuelTypes();
    }
}
