package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.BasicDomainService;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetrolStationService extends BasicDomainService<PetrolStation, PetrolStationDTO, PetrolStationPostDTO> {

    @Autowired
    LocalizationRepository localizationRepository;

    @Override
    public PetrolStationDTO addEntity(PetrolStationPostDTO dto) {
        var petrolStation = postDTOMapper.convertIntoObject(dto);
        var localization = petrolStation.getLocalization();
        localizationRepository.save(localization);
        petrolStation.setLocalization(localization);
        repository.save(petrolStation);
        return mapper.convertIntoDTO(petrolStation);
    }
}
