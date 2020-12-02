package pl.polishstation.polishstationbackend.domain.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.bootstrap.fake.FakeDate;
import pl.polishstation.polishstationbackend.bootstrap.fake.FuelPriceFakeData;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPriceRepository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationService;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTOMapper;
import pl.polishstation.polishstationbackend.domain.statistics.dto.FuelPriceStats;
import pl.polishstation.polishstationbackend.domain.statistics.dto.StatsDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypesNames.DEFAULT_FUEL_TYPES_LIST;

@Service
public class StatisticsService {

    @Autowired
    PetrolStationService petrolStationService;

    @Autowired
    FakeDate fakeDate;

    @Autowired
    PetrolStationRepository petrolStationRepository;

    @Autowired
    FuelTypeRepository fuelTypeRepository;

    @Autowired
    FuelPriceFakeData fuelPriceFakeData;

    @Autowired
    Random random;

    @Autowired
    PetrolStationDTOMapper petrolStationDTOMapper;

    @Autowired
    FuelPriceRepository fuelPriceRepository;

    public StatsDTO makeStatsOfDate() {
        var opinionRank = petrolStationRepository.getTopSortByOpinion(5L).stream()
                .map(petrolStationDTOMapper::convertIntoDTO)
                .collect(Collectors.toList());

        var fuelPriceTypeRank = prepareFuelPriceRank();

        return StatsDTO.builder()
                .day(LocalDateTime.now())
                .opinionRank(opinionRank)
                .fuelTypePriceRank(fuelPriceTypeRank)
                .build();
    }

    private Map<String, List<PetrolStationDTO>> prepareFuelPriceRank() {
        Map<String, List<PetrolStationDTO>> fuelPriceMap = new HashMap<>();

        var listOfFuelTypes = DEFAULT_FUEL_TYPES_LIST.stream()
                .map(fuelTypeName -> fuelTypeRepository.findByName(fuelTypeName).orElseThrow())
                .collect(Collectors.toList());

        listOfFuelTypes.forEach(fuelType -> getRankOfPricesFuelType(fuelPriceMap, fuelType));

        return fuelPriceMap;
    }

    void getRankOfPricesFuelType(Map<String, List<PetrolStationDTO>> fuelPriceMap, FuelType fuelType) {
        var rank = petrolStationRepository.getRankOfPetrolStationPrices(fuelType.getId())
                .stream()
                .map(petrolStationDTOMapper::convertIntoDTO)
                .collect(Collectors.toList());
        fuelPriceMap.put(fuelType.getName(), rank);
    }

    public List<?> getFuelPricesChart(String fuelTypeName, LocalDate dateFrom, LocalDate dateTom, Long petrolStationId) {
        var fuelType = fuelTypeRepository.findByName(fuelTypeName).orElseThrow();
        var fuelPrices = fuelPriceRepository.findAllByPetrolStationIdAndDateBetweenAndFuelTypeOrderByDate(petrolStationId, dateFrom.atStartOfDay(), dateTom.atTime(23, 59), fuelType);
        var fuelPricesNormalized = new LinkedList<FuelPrice>();
        FuelPrice previous = null;
        for (FuelPrice fuelPrice: fuelPrices) {
            if(previous != null) {
                if(previous.getDate().toLocalDate().equals(fuelPrice.getDate().toLocalDate())) {
                    previous = fuelPrice;
                    continue;
                }
            }
            fuelPricesNormalized.add(fuelPrice);
            previous = fuelPrice;
        }
        return fuelPricesNormalized.stream()
                .map(FuelPriceStats::fuelPriceStats)
                .collect(Collectors.toList());
    }

//    private List<PlaceDTO> makeFacilitiesRank(List<PetrolStation> petrolStations) {
//        var petrolStationsRank = petrolStations.stream()
//                .map(petrolStation -> new PetrolStationFacilitiesCount(petrolStation, countFacilities(petrolStation)))
//                .sorted(this::compareFacilities)
//                .limit(10)
//                .map(PetrolStationFacilitiesCount::getPetrolStation)
//                .map(petrolStation -> petrolStationDTOMapper.convertIntoDTO(petrolStation))
//                .map(PlaceDTO::new)
//                .collect(Collectors.toList());
//        for (int i = 0; i < petrolStationsRank.size(); i++) {
//            petrolStationsRank.get(i).setPlaceNumber(i);
//        }
//        return petrolStationsRank;
//    }
//
//    private List<PlaceDTO> makeOpinionRank(List<PetrolStation> petrolStations) {
////        var petrolStationRanl = petrolStations.stream()
////                .map()
//        return null;
//    }
//
//    private int compareFacilities(PetrolStationFacilitiesCount petrolStationFacilitiesCount, PetrolStationFacilitiesCount petrolStationFacilitiesCount1) {
//        return petrolStationFacilitiesCount.getFacilitiesCount().compareTo(petrolStationFacilitiesCount1.getFacilitiesCount());
//    }
//
//    private Long countFacilities(PetrolStation petrolStation) {
//        var listOfFacilities = List.of( petrolStation.getPetrolStationStats().getIsCarWash(),
//        petrolStation.getPetrolStationStats().getIsCompressor(),
//        petrolStation.getPetrolStationStats().getIsHotDogs(),
//        petrolStation.getPetrolStationStats().getIsRestaurant(),
//        petrolStation.getPetrolStationStats().getIsSelfService(),
//        petrolStation.getPetrolStationStats().getIsWC(),
//        petrolStation.getPetrolStationStats().getIsWCFree());
//        return listOfFacilities.stream().filter(x->x).count();
//    }
//
//
//    @Data
//    @AllArgsConstructor
//    static class PetrolStationFacilitiesCount {
//        PetrolStation petrolStation;
//        Long facilitiesCount;
//    }
//
//    List<PetrolStation> fakeListOfPetrolStations() {
//        return IntStream.range(0, 5).mapToObj(i -> fakePetrolStation())
//                .collect(Collectors.toList());
//    }
//
//    private PetrolStation fakePetrolStation() {
//        return PetrolStation.builder()
//                .petrolStationStats(
//                        PetrolStationStats.builder()
//                                .amountOfOpinion((long) (Math.random() * 60))
//                                .isCarWash(Math.random() > .5)
//                                .isHotDogs(Math.random() > .5)
//                                .isCompressor(Math.random() > .5)
//                                .isRestaurant(Math.random() > .5)
//                                .isSelfService(Math.random() > .5)
//                                .isWC(Math.random() > .5)
//                                .isWC(Math.random() > .5)
//                                .build()
//                ).dkn(10L)
//                .fuelPrice(fakeFuelPrices())
//                .opinions(fakeOpinions())
//                .build();
//    }
//
//    private List<Opinion> fakeOpinions() {
//        return IntStream.range(0, 100).mapToObj(i -> fakeOpinion())
//                .collect(Collectors.toList());
//    }
//
//    private Opinion fakeOpinion() {
//        return Opinion.builder()
//                .date(fakeDate.fakeDateOnePastMonth())
//                .mark(random.nextInt(5))
//                .build();
//    }
//
//    private List<FuelPrice> fakeFuelPrices() {
//        return IntStream.range(0, 1000).mapToObj(i -> fakeFuelPrice())
//                .collect(Collectors.toList());
//    }
//
//    private FuelPrice fakeFuelPrice() {
//        var fuelTypes = fuelTypeRepository.findAllByNameIn(FuelTypesNames.FUEL_TYPES_LIST);
//        return FuelPrice.builder()
//                .date(fakeDate.fakeDateOnePastMonth())
//                .fuelType(randomChoice(fuelTypes))
//                .price(fuelPriceFakeData.fakeFuelPriceValue())
//                .build();
//    }
//
//    private <T> T randomChoice(List<T> items) {
//        return items.get((int)Math.ceil(Math.random() * items.size()));
//    }
}
