package pl.polishstation.polishstationbackend.externalapis.googleapi;

import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polishstation.polishstationbackend.utils.geo.Location;

import java.io.IOException;

@RestController
public class GoogleApiController {
    @Autowired
    GoogleApiService googleApiService;

    @GetMapping("/elo")
    PlacesSearchResponse get(@RequestParam String lat, @RequestParam("long") String _long) throws InterruptedException, ApiException, IOException {
        return this.googleApiService.getPetrolStationsOfPosition(new Location(Double.parseDouble(lat), Double.parseDouble(_long)), 10);
    }
}
