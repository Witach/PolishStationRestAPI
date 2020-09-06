package pl.polishstation.polishstationbackend.domain.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTOMapper;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTOMapper;
import pl.polishstation.polishstationbackend.exception.UniqueDataArleadyExists;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static pl.polishstation.polishstationbackend.exception.ExcpetionFactory.uniqueDataExceptionOfClassResolver;

@Service
public class OpinionService {

    @Autowired
    OpinionDTOMapper mapper;

    @Autowired
    OpinionPostDTOMapper opinionPostDTOMapper;

    @Autowired
    PetrolStationRepository petrolStationRepository;

    @Autowired
    OpinionRpository rpository;


    public OpinionDTO addEntity(OpinionPostDTO dto) {
        var opinion = opinionPostDTOMapper.convertIntoObject(dto);
        return  mapper.convertIntoDTO(
                rpository.save(opinion)
        );
    }

    public void deleteEntity(Long id) {
        if(!rpository.existsById(id))
            throw new EntityDoesNotExists();
        rpository.deleteById(id);
    }

    public void updateEntity(OpinionDTO dto, Long id) {
        if(!rpository.existsById(id))
            throw new EntityDoesNotExists();
        var opinion = mapper.convertIntoObject(dto);
        rpository.save(opinion);
    }

    public OpinionDTO getEntityById(Long id) {
        return rpository.findById(id)
                .map(mapper::convertIntoDTO)
                .orElseThrow(EntityDoesNotExists::new);
    }

    public List<OpinionDTO> getAllEntities() {
        return rpository.findAll().stream()
                .map(mapper::convertIntoDTO)
                .collect(Collectors.toList());
    }
}
