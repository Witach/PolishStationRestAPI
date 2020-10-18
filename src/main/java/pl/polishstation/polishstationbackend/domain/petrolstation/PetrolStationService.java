package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.apiutils.filtring.FilterDomainService;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPriceRepository;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.LastFuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTOMapper;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.spec.PetrolStationSpecFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetrolStationService extends FilterDomainService<PetrolStation, PetrolStationDTO, PetrolStationPostDTO> {

    @Autowired
    LocalizationRepository localizationRepository;

    @Autowired
    FuelPriceRepository fuelPriceRepository;

    @Autowired
    PetrolStationDTOMapper petrolStationDTOMapper;

    @Autowired
    PetrolStationSpecFactory petrolStationSpecFactory;

    @Autowired
    PetrolStationRepository petrolStationRepository;

    @Override
    public PetrolStationDTO addEntity(PetrolStationPostDTO dto) {
        var petrolStation = postDTOMapper.convertIntoObject(dto);
        var localization = petrolStation.getLocalization();
        localizationRepository.save(localization);
        petrolStation.setLocalization(localization);
        repository.save(petrolStation);
        return mapper.convertIntoDTO(petrolStation);
    }

    @Override
    public Page<PetrolStationDTO> getAllEntities(Pageable pageable) {
       var pageOfItems =  super.getAllEntities(pageable);
       pageOfItems.stream().forEach(petrolStationDTO ->
               petrolStationDTO.setFuelPriceDTO(
                       getLastPricesOfFuelsForPetrolStation(petrolStationDTO)
               )
       );
       return pageOfItems;
    }

    List<LastFuelPriceDTO> getLastPricesOfFuelsForPetrolStation(PetrolStationDTO petrolStation) {
        return fuelPriceRepository.findLastPricesOfTypeOfPetrolStation(petrolStation.getId()).stream()
                .map(petrolStationDTOMapper::convertIntoLastFuelPriceDTO)
                .collect(Collectors.toList());
    }

    List<PetrolStationDTO> searchForPetrolStations(Sort sort, MultiValueMap<String, String> multiValueMap) {
        return petrolStationRepository.findAll(petrolStationSpecFactory.specificationFrom(multiValueMap), sort).stream()
                .map(petrolStationDTOMapper::convertIntoDTO)
                .collect(Collectors.toList());
    }
}
