package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.aspect.ScorePoints;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.user.appuser.NotificationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuelPriceService extends BasicDomainService<FuelPrice, FuelPriceDTO, FuelPricePostDTO> {

    @Autowired
    FuelPriceRepository fuelPriceRepository;

    @Autowired
    NotificationService notificationService;

    ResMessage addEntityClosure(FuelPricePostDTO fuelPricePostDTO) {
        try {
            var fuelPriceDto = addEntity(fuelPricePostDTO);
            return fuelPriceDto.getVerified() ? ResMessage.builder()
                    .message("Your price has been successfully added")
                    .build()
                    : ResMessage.builder().
                    message("Your price is ready to be verified")
                    .build();
        } catch (ToMuchDiffrenvceException | FirebaseMessagingException e) {
            return ResMessage.builder()
                    .message(e.getMessage())
                    .build();
        }
    }

    @ScorePoints
    @Override
    @Transactional
    public FuelPriceDTO addEntity(FuelPricePostDTO fuelPricePostDTO) throws FirebaseMessagingException {
        var fuelPrice = postDTOMapper.convertIntoObject(fuelPricePostDTO);
        var isVerified = fuelPrice.getUser().getAmountOfPoints() > 50;
        fuelPrice.setVerified(isVerified);
        if(!isVerified) {
            var fuelTypes = fuelPriceRepository.findAllByPetrolStationAndFuelTypeOrderByDateAsc(fuelPrice.getPetrolStation(), fuelPrice.getFuelType());
            if(!fuelTypes.isEmpty()) {
                verifyPrice(fuelPrice, fuelTypes);
            }
        }
        fuelPrice.setDate(LocalDateTime.now());
        if (fuelPrice.getVerified()) {
            notifyAttachedUsers(fuelPrice);
        }
        return mapper.convertIntoDTO(
                repository.save(fuelPrice)
        );
    }

    private void notifyAttachedUsers(FuelPrice fuelPrice) throws FirebaseMessagingException {
        var tokensToServe = fuelPrice.getPetrolStation()
                .getLovedUsers()
                .stream()
                .map(AppUser::getFirebaseToken)
                .filter(Objects::nonNull)
                .filter(token -> !token.isBlank())
                .collect(Collectors.toList());

        notificationService.notifyUsersAboutNewFuelPrice(tokensToServe, fuelPrice);
    }

    private void verifyPrice(FuelPrice fuelPrice, java.util.List<FuelPrice> fuelTypes) {
        var lastFuelPriceOfType = fuelTypes.get(0);
        var isSameUser = lastFuelPriceOfType.getUser().getId().equals(fuelPrice.getUser().getId());
        var isTheSameAmount = lastFuelPriceOfType.getPrice().equals(fuelPrice.getPrice()) ;
        var lastVerifiredFuelPriceOpt = getLastVerified(fuelTypes);
        if(lastVerifiredFuelPriceOpt.isPresent() && calculatePercentageOfChange(lastVerifiredFuelPriceOpt.get().getPrice(), fuelPrice.getPrice()) > 10) {
            throw new ToMuchDiffrenvceException("Price is too different than previous one");
        }
        fuelPrice.setVerified(!isSameUser && isTheSameAmount);
    }

    private Optional<FuelPrice> getLastVerified(List<FuelPrice> fuelPrices) {
        for (var fuelPrice: fuelPrices) {
            if(fuelPrice.getVerified())
                return Optional.of(fuelPrice);
        }
        return Optional.empty();

    }

    private Double calculatePercentageOfChange(Double oldValue, Double newValue) {
        return Math.abs(((oldValue - newValue) / oldValue) * 100);
    }
}
