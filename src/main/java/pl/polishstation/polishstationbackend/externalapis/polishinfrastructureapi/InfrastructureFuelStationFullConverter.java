package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypesNames.*;
import static pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.PetrolStationAttachmentDecorator.wrapPetrolstation;

@Component
public class InfrastructureFuelStationFullConverter {

    @Autowired
    InfrastructureFuelStationDTOMapper mapper;

    @Autowired
    FuelTypeRepository fuelTypeRepository;

    List<PetrolStation> convertIntoBidirectionalEntity(List<InfrastructureFuelStationDTO> infrastructureFuelStationDTO) {
        return infrastructureFuelStationDTO.stream()
                .map(this::convertIntoBidirectionalEntity)
                .map(this::marshallAddresss)
                .collect(Collectors.toList());
    }

    private PetrolStation marshallAddresss(PetrolStation petrolStation) {
        if(isNull(petrolStation.getLocalization().getNumber()) || petrolStation.getLocalization().getNumber().isBlank()) {
            var parts = petrolStation.getLocalization().getStreet().split(" ");
            petrolStation.getLocalization().setNumber(parts[parts.length -1]);
            StringBuilder streeName = new StringBuilder();
            for (int i = 0; i < parts.length -1; i++) {
                streeName.append(parts[i]);
                if( i != parts.length -1)
                    streeName.append(" ");
            }
            petrolStation.getLocalization().setStreet(streeName.toString());
        }
        return petrolStation;
    }

    PetrolStation convertIntoBidirectionalEntity(InfrastructureFuelStationDTO infrastructureFuelStationDTO) {
        var fuelStation = mapper.convertIntoObject(infrastructureFuelStationDTO);
        fuelStation.setFuelTypes(new LinkedList<>());
        return wrapPetrolstation(fuelStation, fuelTypeRepository)
                .attachFuelTypeByGetBoolean(infrastructureFuelStationDTO::getIsDiesel, DIESEL)
                .attachFuelTypeByGetBoolean(infrastructureFuelStationDTO::getIsFuelOil, FUELOIL)
                .attachFuelTypeByGetBoolean(infrastructureFuelStationDTO::getIsLPG, LPG)
                .attachFuelTypeByGetBoolean(infrastructureFuelStationDTO::getIsPetrol, PETROL)
                .attachFuelTypeByGetBoolean(infrastructureFuelStationDTO::getIsPetrol, PETROL2)
                .attachFuelTypeByGetBoolean(infrastructureFuelStationDTO::getIsZLOOcase, ZLOOCASE)
                .getPetrolStation();
    }


}
