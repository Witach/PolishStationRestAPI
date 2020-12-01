package pl.polishstation.polishstationbackend.bootstrap.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypesNames.DEFAULT_FUEL_TYPES_LIST;
import static pl.polishstation.polishstationbackend.utils.Indexator.getEndIndexOfPart;
import static pl.polishstation.polishstationbackend.utils.Indexator.getStartIndexOfPath;

@Component
@Profile("dev")
public class FuelPriceFakeData {
    static final List<String> FUEL_TYPES = DEFAULT_FUEL_TYPES_LIST;
    @Autowired
    private FakeDate fakeDate;


    public List<FuelPrice> fakeFuelPrices(List<FuelType> fuelTypes, List<AppUser> appUsers) {
        List<FuelPrice> fuelPrices = new LinkedList<FuelPrice>();
        for (var fuelType : fuelTypes) {
            var fakeFuelPriceOfType = IntStream.range(0, 100)
                    .mapToObj(i -> fakeFuelPriceOfType(fuelType))
                    .collect(Collectors.toList());
            fuelPrices.addAll(fakeFuelPriceOfType);
        }
        Collections.shuffle(fuelPrices);
        IntStream.range(0, appUsers.size())
                .forEach(v -> {
                    var appUser = appUsers.get(v);
                    var fuelPricePart = fuelPrices.subList(getStartIndexOfPath(v), getEndIndexOfPart(v));
                    appUser.setFuelPrices(fuelPricePart);
                    fuelPricePart.forEach(fuelPrice -> fuelPrice.setUser(appUser));
                });

        return fuelPrices;
    }

    public FuelPrice fakeFuelPriceOfType(FuelType fuelType) {
        var fuelPrice = FuelPrice.builder()
                .date(fakeDate.fakeDateOnePastMonth())
                .fuelType(fuelType)
                .price(fakeFuelPriceValue())
                .build();
        fuelType.getFuelPrices().add(fuelPrice);
        return fuelPrice;
    }

    public Double fakeFuelPriceValue() {
        return Math.round(Math.random() * 6. * 100.) / 100.;
    }

    public List<FuelType> fakeFuelTypes() {
        return FUEL_TYPES.stream()
                .map(FuelType::new)
                .collect(Collectors.toList());
    }
}
