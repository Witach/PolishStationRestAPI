package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;

@Component
public class FuelPricePostDTOMapper {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    FuelTypeRepository fuelTypeRepository;
    @Autowired
    PetrolStationRepository petrolStationRepository;

    public FuelPrice convertIntoObject(FuelPricePostDTO fuelPricePostDTO) {
        var fuelType = fuelTypeRepository.findByName(fuelPricePostDTO.getFuelType())
                .orElseThrow(EntityDoesNotExists::new);
        var appUser = appUserRepository.findById(fuelPricePostDTO.getAppUserId())
                .orElseThrow(EntityDoesNotExists::new);
        var  petrolStation = petrolStationRepository.findById(fuelPricePostDTO.getPetrolStationId())
                .orElseThrow(EntityDoesNotExists::new);
        return FuelPrice.builder()
                .fuelType(fuelType)
                .date(fuelPricePostDTO.getDate())
                .price(fuelPricePostDTO.getPrice())
                .petrolStation(petrolStation)
                .user(appUser)
                .build();
    }
}
