package pl.polishstation.polishstationbackend.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.utils.geo.Location;


import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;

@Service
public class LocalizationCacher {

    @Autowired
    private LocalizationRepository localizationRepository;

    public void cacheLocalizationInfo(Localization localization, Location location) {
        var isDiffrenet = isLocalizationDifferent(localization, location);
        if(isDiffrenet)
            mergeChanges(localization, location);
    }

    private void mergeChanges(Localization localization, Location location) {
        localization.setLat(location.getLat().toString());
        localization.set_long(location.get_long().toString());
        localizationRepository.save(localization);
    }

    private boolean isLocalizationDifferent(final Localization localization, final Location location) {
        return nonNull(localization.get_long()) &&
                nonNull(localization.getLat()) &&
                parseLong(localization.get_long()) != location.get_long() ||
                parseLong(localization.getLat()) != location.getLat();
    }
}
