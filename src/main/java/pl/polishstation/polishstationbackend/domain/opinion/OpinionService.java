package pl.polishstation.polishstationbackend.domain.opinion;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.aspect.ScorePoints;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;

import javax.transaction.Transactional;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpinionService extends BasicDomainService<Opinion, OpinionDTO, OpinionPostDTO> {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OpinionRpository opinionRpository;

    @Autowired
    PetrolStationRepository petrolStationRepository;

    @ScorePoints
    @Override
    public OpinionDTO addEntity(OpinionPostDTO opinionPostDTO) throws FirebaseMessagingException {
        var opionionDTO = super.addEntity(opinionPostDTO);
        updateAvgOpinion(opinionPostDTO);
        return opionionDTO;
    }

    @Transactional
    public void updateAvgOpinion(OpinionPostDTO opinionPostDTO) {
        var petrolStation = petrolStationRepository.findById(opinionPostDTO.getPetrolStationId())
                .orElseThrow();

        var sumOfOpinions = petrolStation.getOpinions().stream()
                .mapToDouble(opinion -> (double)opinion.getMark())
                .reduce(0, Double::sum);

        var countOfOpinion = petrolStation.getOpinions().size();

        double res = roundToSecondDecimal(sumOfOpinions / (double) countOfOpinion);
        petrolStation.getPetrolStationStats().setAvgOpinion(res);
        petrolStation.getPetrolStationStats().setAmountOfOpinion((long) countOfOpinion);
        petrolStationRepository.save(petrolStation);
    }

    private double roundToSecondDecimal(double number) {
        var df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        var formated = df.format(number);
        return Double.parseDouble(formated);
    }

    public List<OpinionDTO> getUsersOpinion(String email) {
        var user = appUserRepository.findAppUserByEmailEquals(email).orElseThrow();
        return opinionRpository.findAllByUser(user).stream().map(mapper::convertIntoDTO).collect(Collectors.toList());
    }
}
