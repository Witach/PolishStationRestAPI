package pl.polishstation.polishstationbackend.bootstrap.fake;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStationStats;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.polishstation.polishstationbackend.utils.Indexator.getEndIndexOfPart;
import static pl.polishstation.polishstationbackend.utils.Indexator.getStartIndexOfPath;

@Component
@Profile("dev")
public class PetrolStationFakeData {
    @Autowired
    private Faker faker;
    @Autowired
    private Random random;


    public List<PetrolStation> fakePetrolStations(List<FuelPrice> fuelPrices, List<Opinion> opinions, List<Localization> localizations) {
        return IntStream.range(0, 5)
                .mapToObj(v -> fakePetrolStationBounded(localizations, fuelPrices, opinions, v + 1))
                .collect(Collectors.toList());
    }

    public PetrolStation fakePetrolStationBounded(List<Localization> localizations, List<FuelPrice> fuelPrice, List<Opinion> opinions, int v) {
        var partOfFuelPrice = fuelPrice.subList(getStartIndexOfPath(v), getEndIndexOfPart(v));
        var partOfOpinions = opinions.subList(getStartIndexOfPath(v), getEndIndexOfPart(v));
        return fakePetrolStation(partOfFuelPrice, partOfOpinions, localizations.get(v));
    }

    public PetrolStation fakePetrolStation(List<FuelPrice> fuelPrices, List<Opinion> opinions, Localization localization) {
        var petrolStation = PetrolStation.builder()
                .dkn(random.nextLong())
                .name(faker.animal().name())
                .fuelPrice(fuelPrices)
                .opinions(opinions)
                .localization(localization)
                .build();

        opinions.forEach(opinion -> opinion.setPetrolStation(petrolStation));
        fuelPrices.forEach(fuelPrice -> fuelPrice.setPetrolStation(petrolStation));

        var fuelTypes = fuelPrices.stream()
                .map(FuelPrice::getFuelType)
                .distinct()
                .collect(Collectors.toList());

        var sum = opinions.stream()
                .map(Opinion::getMark)
                .reduce(0, Integer::sum);

        var petrolStationStats = PetrolStationStats.builder()
                .amountOfOpinion((long) opinions.size())
                .avgOpinion(sum / opinions.size())
                .build();

        localization.setPetrolStation(petrolStation);
        petrolStation.setFuelTypes(fuelTypes);
        petrolStation.setPetrolStationStats(petrolStationStats);

        return petrolStation;


    }
}
