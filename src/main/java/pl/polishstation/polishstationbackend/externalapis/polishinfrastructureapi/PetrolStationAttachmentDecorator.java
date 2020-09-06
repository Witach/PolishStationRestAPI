package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;

import java.util.function.Supplier;

public class PetrolStationAttachmentDecorator {
    PetrolStation petrolStation;
    FuelTypeRepository fuelTypeRepository;

    private PetrolStationAttachmentDecorator(PetrolStation petrolStation, FuelTypeRepository fuelTypeRepository) {
        this.petrolStation = petrolStation;
        this.fuelTypeRepository = fuelTypeRepository;
    }

    public static PetrolStationAttachmentDecorator wrapPetrolstation(PetrolStation petrolStation, FuelTypeRepository fuelTypeRepository) {
                return new PetrolStationAttachmentDecorator(petrolStation, fuelTypeRepository);
    }

    public PetrolStationAttachmentDecorator attachFuelTypeByGetBoolean(Supplier<Boolean> supplier, String fuelTypeName) {
        if(supplier.get()) {
            var fuelType = fuelTypeRepository.findByName(fuelTypeName)
                    .orElseThrow(EntityDoesNotExists::new);
            petrolStation.addFuelType(fuelType);
        }
        return this;
    }

    public PetrolStation getPetrolStation() {
        return petrolStation;
    }
}
