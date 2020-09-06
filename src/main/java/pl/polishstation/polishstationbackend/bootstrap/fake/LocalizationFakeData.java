package pl.polishstation.polishstationbackend.bootstrap.fake;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Profile("dev")
public class LocalizationFakeData {
    static final List<String> PROVINCE_NAMES = Collections.unmodifiableList(List.of("MAZOWIECKIE", "Łódzkie", "ŚLĄSKIE", "LUBELSKIE"));
    @Autowired
    Faker faker;
    @Autowired
    Random random;

    public List<Localization> fakeLocalizations() {
        return IntStream.range(0, 20)
                .mapToObj(v -> fakeLocalization())
                .collect(Collectors.toList());
    }

    public Localization fakeLocalization() {
        var address = faker.address();
        return Localization.builder()
                .street(address.streetName())
                .province(PROVINCE_NAMES.get(random.nextInt(PROVINCE_NAMES.size() - 1)))
                .number(address.buildingNumber())
                .postalCode(address.zipCode())
                .name(address.cityName())
                .lat(fakeGeographicalPosition())
                ._long(fakeGeographicalPosition())
                .build();
    }

    public String fakeGeographicalPosition() {
        var longString = String.valueOf(random.nextFloat());
        return longString.length() > 8 ? longString.substring(0, 8) : longString;
    }
}
