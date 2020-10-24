package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.localization.Localization;

import static java.util.Objects.isNull;

@Component
public class AddressFormater {

    public String formatAddressStringFromLocalization(Localization localization) {
        if (!(isNull(localization.getFormattedAddress()) || localization.getFormattedAddress().isBlank())) {
            return this.noop(localization);
        } else if (isNull(localization.getNumber()) || localization.getNumber().isBlank()) {
            return this.formatAddressWithoutNumber(localization);
        }
        return this.formatAddressWithFullAddressInfo(localization);
    }

    String formatAddressWithoutNumber(Localization localization) {
        return localization.getStreet() + "," + " " + localization.getPostalCode() + " " + localization.getName();
    }

    String formatAddressWithFullAddressInfo(Localization localization) {
        return localization.getStreet() + " " + localization.getNumber() + "," + " " + localization.getPostalCode() + " " + localization.getName();
    }

    String noop(Localization localization) {
        return localization.getFormattedAddress();
    }
}
