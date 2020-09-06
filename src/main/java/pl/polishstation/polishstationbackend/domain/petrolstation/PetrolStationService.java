package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetrolStationService {

    @Autowired
    LocalizationRepository localizationRepository;

    @Autowired
    PetrolStationRepository petrolStationRepository;

    @Autowired
    PetrolStationDTOMapper petrolStationDTOMapper;

    public PetrolStationDTO addEntity(PetrolStationPostDTO dto) {
        var petrolStation = petrolStationDTOMapper.convertIntoObject(dto);
        var localization = petrolStation.getLocalization();
        localizationRepository.save(localization);
        petrolStation.setLocalization(localization);
        petrolStationRepository.save(petrolStation);
        return petrolStationDTOMapper.convertIntoDTO(petrolStation);
    }

    public void deleteEntity(Long id) {
        if(!petrolStationRepository.existsById(id))
            throw new EntityDoesNotExists();
        petrolStationRepository.deleteById(id);
    }

    public void updateEntity(PetrolStationDTO dto, Long id) {
        if(!petrolStationRepository.existsById(id))
            throw new EntityDoesNotExists();
        var petrolStation = petrolStationDTOMapper.convertIntoObject(dto);
        petrolStationRepository.save(petrolStation);
    }

    public PetrolStationDTO getEntityById(Long id) {
        return petrolStationRepository.findById(id)
                .map(petrolStationDTOMapper::convertIntoDTO)
                .orElseThrow(EntityDoesNotExists::new);
    }

    public List<PetrolStationDTO> getAllEntities() {
        return petrolStationRepository.findAll().stream()
                .map(petrolStationDTOMapper::convertIntoDTO)
                .collect(Collectors.toList());
    }
}
