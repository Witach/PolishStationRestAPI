package pl.polishstation.polishstationbackend.domain.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.statistics.dto.FuelPriceStatsDTO;
import pl.polishstation.polishstationbackend.domain.statistics.dto.StatsDTO;

import java.time.LocalDate;

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
    public FuelPriceStatsDTO getStatsAboutStation(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTom, @RequestParam Long petrolStationId) {
        return statisticsService.getFuelPricesChart(dateFrom, dateTom, petrolStationId);
    }
}
