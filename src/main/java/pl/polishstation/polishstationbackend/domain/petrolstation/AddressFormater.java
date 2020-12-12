package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;

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

    public Localization decodeFormattedString(Localization localization) {
        if(isNull(localization.getStreet()) || localization.getStreet().isBlank()) {
            var addresParts = localization.getFormattedAddress().split(",");
            var streetParts = new String[1];
            if (addresParts.length > 1) {
                streetParts = addresParts[0].split(" ");
            }
            localization.setNumber(streetParts[streetParts.length -1]);
            var streetName = new StringBuilder("");
            for (int i = 0; i < streetParts.length -1; i++) {
                streetName.append(streetParts[i]);
                if( i != streetParts.length -1)
                    streetName.append(" ");
            }
            localization.setStreet(streetName.toString());
            if(addresParts.length > 1) {
                var townParts = addresParts[1].split(" ");
                localization.setPostalCode(townParts[0]);
                var townName = new StringBuilder("");
                for (int i = 1; i < townParts.length; i++) {
                    townName.append(townParts[i]);
                    if( i != townParts.length -1)
                        townName.append(" ");
                }
                localization.setName(townName.toString());
            }
        }
        return localization;
    }

    public LocalizationDTO decodeFormattedString(LocalizationDTO localization) {
        if(isNull(localization.getStreet()) || localization.getStreet().isBlank()) {
            var addresParts = localization.getFormattedAddress().split(",");
            var streetParts = new String[1];
            if (addresParts.length > 1) {
                streetParts = addresParts[0].split(" ");
            }
            localization.setNumber(streetParts[streetParts.length -1]);
            var streetName = new StringBuilder("");
            for (int i = 0; i < streetParts.length -1; i++) {
                streetName.append(streetParts[i]);
                if( i != streetParts.length -1)
                    streetName.append(" ");
            }
            localization.setStreet(streetName.toString());
            if(addresParts.length > 1) {
                var townParts = addresParts[1].split(" ");
                localization.setPostalCode(townParts[1]);
//                var townName = new StringBuilder("");
//                for (int i = 1; i < townParts.length; i++) {
//                    townName.append(townParts[i]);
//                    if( i != townParts.length -1)
//                        townName.append(" ");
//                }
                if (townParts.length >= 3) {
                    localization.setName(townParts[2]);
                }
            }
        }
        return localization;
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
