package pl.polishstation.polishstationbackend.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import pl.polishstation.polishstationbackend.bootstrap.fake.FakeDate;
import pl.polishstation.polishstationbackend.bootstrap.fake.FuelPriceFakeData;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypesNames;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationService;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTOMapper;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStationStats;
import pl.polishstation.polishstationbackend.domain.statistics.dto.PlaceDTO;
import pl.polishstation.polishstationbackend.domain.statistics.dto.StatsDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatisticsService {

    @Autowired
    PetrolStationService petrolStationService;

    @Autowired
    FakeDate fakeDate;

    @Autowired
    FuelTypeRepository fuelTypeRepository;

    @Autowired
    FuelPriceFakeData fuelPriceFakeData;

    @Autowired
    Random random;

    @Autowired
    PetrolStationDTOMapper petrolStationDTOMapper;

    StatsDTO getStatsAboutRegion(MultiValueMap<String, String> valueMap){
        var userParams = petrolStationService.extractUserData(valueMap).orElseThrow();
        var fuelStations = fakeListOfPetrolStations();
//        var petrolStations = petrolStationService.filterStationsWithDistanceNoDTO(fuelStations, userParams);
        var dateOfStats = LocalDateTime.now().minusDays(2);
        return makeStatsOfDate(dateOfStats);
    }

    private StatsDTO makeStatsOfDate(LocalDateTime dateOfStats, List<PetrolStation> petrolStations) {
        List<PlaceDTO> facilitiesRank = makeFacilitiesRank(petrolStations);
    }

    private List<PlaceDTO> makeFacilitiesRank(List<PetrolStation> petrolStations) {
        var petrolStationsRank = petrolStations.stream()
                .map(petrolStation -> new PetrolStationFacilitiesCount(petrolStation, countFacilities(petrolStation)))
                .sorted(this::compareFacilities)
                .limit(10)
                .map(PetrolStationFacilitiesCount::getPetrolStation)
                .map(petrolStation -> petrolStationDTOMapper.convertIntoDTO(petrolStation))
                .map(PlaceDTO::new)
                .collect(Collectors.toList());
        for (int i = 0; i < petrolStationsRank.size(); i++) {
            petrolStationsRank.get(i).setPlaceNumber(i);
        }
        return petrolStationsRank;
    }

    private List<PlaceDTO> makeOpinionRank(List<PetrolStation> petrolStations) {
        var petrolStationRanl = petrolStations.stream()
                .map()
    }

    private int compareFacilities(PetrolStationFacilitiesCount petrolStationFacilitiesCount, PetrolStationFacilitiesCount petrolStationFacilitiesCount1) {
        return petrolStationFacilitiesCount.getFacilitiesCount().compareTo(petrolStationFacilitiesCount1.getFacilitiesCount());
    }

    private Long countFacilities(PetrolStation petrolStation) {
        var listOfFacilities = List.of( petrolStation.getPetrolStationStats().getIsCarWash(),
        petrolStation.getPetrolStationStats().getIsCompressor(),
        petrolStation.getPetrolStationStats().getIsHotDogs(),
        petrolStation.getPetrolStationStats().getIsRestaurant(),
        petrolStation.getPetrolStationStats().getIsSelfService(),
        petrolStation.getPetrolStationStats().getIsWC(),
        petrolStation.getPetrolStationStats().getIsWCFree());
        return listOfFacilities.stream().filter(x->x).count();
    }


    @Data
    @AllArgsConstructor
    static class PetrolStationFacilitiesCount {
        PetrolStation petrolStation;
        Long facilitiesCount;
    }

    List<PetrolStation> fakeListOfPetrolStations() {
        return IntStream.range(0, 5).mapToObj(i -> fakePetrolStation())
                .collect(Collectors.toList());
    }

    private PetrolStation fakePetrolStation() {
        return PetrolStation.builder()
                .petrolStationStats(
                        PetrolStationStats.builder()
                                .amountOfOpinion((long) (Math.random() * 60))
                                .isCarWash(Math.random() > .5)
                                .isHotDogs(Math.random() > .5)
                                .isCompressor(Math.random() > .5)
                                .isRestaurant(Math.random() > .5)
                                .isSelfService(Math.random() > .5)
                                .isWC(Math.random() > .5)
                                .isWC(Math.random() > .5)
                                .build()
                ).dkn(10L)
                .fuelPrice(fakeFuelPrices())
                .opinions(fakeOpinions())
                .build();
    }

    private List<Opinion> fakeOpinions() {
        return IntStream.range(0, 100).mapToObj(i -> fakeOpinion())
                .collect(Collectors.toList());
    }

    private Opinion fakeOpinion() {
        return Opinion.builder()
                .date(fakeDate.fakeDateOnePastMonth())
                .mark(random.nextInt(5))
                .build();
    }

    private List<FuelPrice> fakeFuelPrices() {
        return IntStream.range(0, 1000).mapToObj(i -> fakeFuelPrice())
                .collect(Collectors.toList());
    }

    private FuelPrice fakeFuelPrice() {
        var fuelTypes = fuelTypeRepository.findAllByNameIn(FuelTypesNames.FUEL_TYPES_LIST);
        return FuelPrice.builder()
                .date(fakeDate.fakeDateOnePastMonth())
                .fuelType(randomChoice(fuelTypes))
                .price(fuelPriceFakeData.fakeFuelPriceValue())
                .build();
    }

    private <T> T randomChoice(List<T> items) {
        return items.get((int)Math.ceil(Math.random() * items.size()));
    }
}
