package pl.polishstation.polishstationbackend.domain.statistics;

import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.statistics.dto.StatsDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Loggable
@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @ResponseStatus(OK)
    @GetMapping("/rank")
    public StatsDTO get(){
        return statisticsService.makeStatsOfDate();
    }

    @ResponseStatus(OK)
    @GetMapping("/petrol-station")
    public List<?> getStatsAboutStation(@RequestParam String fuelTypeName, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTom, @RequestParam Long petrolStationId) {
        return statisticsService.getFuelPricesChart(fuelTypeName, dateFrom, dateTom, petrolStationId);
    }
}
