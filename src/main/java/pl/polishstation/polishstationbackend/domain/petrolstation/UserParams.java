package pl.polishstation.polishstationbackend.domain.petrolstation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.utils.geo.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserParams {
    Location location;
    Double maxDistance;
}
