package pl.polishstation.polishstationbackend.domain.statistics;

import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.statistics.dto.StatsDTO;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@Loggable
@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @ResponseStatus(OK)
    @GetMapping
    public StatsDTO get(@RequestParam MultiValueMap<String, String> filtringParams) throws InterruptedException, ApiException, IOException {
        return statisticsService.getStatsAboutRegion(filtringParams);
    }
}
