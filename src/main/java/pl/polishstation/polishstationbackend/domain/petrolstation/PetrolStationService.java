package pl.polishstation.polishstationbackend.domain.petrolstation;

import com.google.maps.errors.ApiException;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import pl.polishstation.polishstationbackend.apiutils.filtring.FilterDomainService;
import pl.polishstation.polishstationbackend.cache.LocalizationCacher;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPriceRepository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypesNames;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTOMapper;
import pl.polishstation.polishstationbackend.domain.opinion.OpinionRpository;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTOMapper;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.LastFuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTOMapper;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStationStats;
import pl.polishstation.polishstationbackend.domain.petrolstation.spec.PetrolStationSpecFactory;
import pl.polishstation.polishstationbackend.externalapis.googleapi.GoogleApiService;
import pl.polishstation.polishstationbackend.externalapis.googleapi.GoogleMapper;
import pl.polishstation.polishstationbackend.utils.geo.GeoCalculator;
import pl.polishstation.polishstationbackend.utils.geo.Location;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static pl.polishstation.polishstationbackend.utils.geo.Location.parseLocalizationDTO;

@Service
public class PetrolStationService extends FilterDomainService<PetrolStation, PetrolStationDTO, PetrolStationPostDTO> {

    @Autowired
    LocalizationRepository localizationRepository;

    @Autowired
    FuelPriceRepository fuelPriceRepository;

    @Autowired
    PetrolStationDTOMapper petrolStationDTOMapper;

    @Autowired
    PetrolStationSpecFactory petrolStationSpecFactory;

    @Autowired
    PetrolStationRepository petrolStationRepository;

    @Autowired
    GoogleApiService googleApiService;

    @Autowired
    LocalizationCacher localizationCacher;

    @Autowired
    GoogleMapper googleMapper;

    @Autowired
    AddressFormater addressFormater;

    @Autowired
    FuelTypeRepository fuelTypeReposit;

    @Autowired
    OpinionRpository opinionRpository;

    @Autowired
    OpinionDTOMapper opinionDTOMapper;

    @Autowired
    LocalizationDTOMapper localizationDTOMapper;

    @Override
    public PetrolStationDTO addEntity(PetrolStationPostDTO dto) {
        var petrolStation = postDTOMapper.convertIntoObject(dto);
        var localization = petrolStation.getLocalization();
        marshallAddressData(localization);
        this.fetchGeoPositionIfNotExists(petrolStation);
        localizationRepository.save(localization);
        petrolStation.setLocalization(localization);
        attachDefaultTypesToPetrolStation(petrolStation);
        petrolStation.setPetrolStationStats(new PetrolStationStats());
        repository.save(petrolStation);
        return mapper.convertIntoDTO(petrolStation);
    }

    public PetrolStationDTO addEntity(PetrolStationDTO petrolStationDTO) {
        var petrolStation = petrolStationDTOMapper.convertIntoObject(petrolStationDTO);
        marshallAddressData(petrolStation.getLocalization());
        var persistedLocalization = localizationRepository.save(petrolStation.getLocalization());
        petrolStation.setLocalization(persistedLocalization);
        persistedLocalization.setPetrolStation(petrolStation);
        attachDefaultTypesToPetrolStation(petrolStation);
        petrolStation.setPetrolStationStats(new PetrolStationStats());
        petrolStation = repository.save(petrolStation);
        return mapper.convertIntoDTO(petrolStation);
    }

    public void attachDefaultTypesToPetrolStation(PetrolStation petrolStation) {
        var defaultTypes = fuelTypeReposit.findAll().stream().filter(fuelType ->
                FuelTypesNames.DEFAULT_FUEL_TYPES_LIST.stream()
                        .map(String::toString)
                        .anyMatch(name -> name.equals(fuelType.getName()))
        ).collect(Collectors.toList());
        petrolStation.setFuelTypes(defaultTypes);
        defaultTypes.forEach(fuelType -> fuelType.getPetrolStations().add(petrolStation));
    }

    public void marshallAddressData(Localization localization) {
        var formattedAddress = addressFormater.formatAddressStringFromLocalization(localization);
        localization.setFormattedAddress(formattedAddress);
        addressFormater.decodeFormattedString(localization);
    }

    @Override
    public Page<PetrolStationDTO> getAllEntities(Pageable pageable) {
       var pageOfItems =  super.getAllEntities(pageable);
       pageOfItems.stream().forEach(petrolStationDTO ->
               petrolStationDTO.setFuelPriceDTO(
                       getLastPricesOfFuelsForPetrolStation(petrolStationDTO)
               )
       );
       return pageOfItems;
    }

    public List<LastFuelPriceDTO> getLastPricesOfFuelsForPetrolStation(PetrolStationDTO petrolStation) {
        return fuelPriceRepository.findLastPricesOfTypeOfPetrolStation(petrolStation.getId()).stream()
                .map(petrolStationDTOMapper::convertIntoLastFuelPriceDTO)
                .collect(Collectors.toList());
    }

    List<PetrolStationDTO> searchForPetrolStations(Sort sort, MultiValueMap<String, String> multiValueMap) throws InterruptedException, ApiException, IOException {
        var querryResult = petrolStationRepository.findAll(petrolStationSpecFactory.specificationFrom(multiValueMap), sort).stream()
                .map(petrolStationDTOMapper::convertIntoDTO)
                .collect(Collectors.toList());

        var userParamsOptional = extractUserData(multiValueMap);
        if(userParamsOptional.isEmpty())
            return querryResult;
        var userParams = userParamsOptional.orElseThrow();

        var googleQuerryResult = googleApiService.getPetrolStationsOfPosition(userParams.location, (int)Math.round(userParams.maxDistance)).results;

        cacheUnknownStations(querryResult, googleQuerryResult);

        if(!sort.isEmpty() && sort.get().collect(Collectors.toList()).get(0).equals("distance")) {
            return filterStationListWithDistanceSort(querryResult, userParams);
        }
        return filterListOfPetrolsByUserDistance(querryResult, userParams);
    }

    public void cacheUnknownStations(List<PetrolStationDTO> querryResult, com.google.maps.model.PlacesSearchResult[] googleQuerryResult) {
        var newPetrols = Arrays.stream(googleQuerryResult)
                .map(googleMapper::convertFromGoogleDto)
                .peek(petrol -> addressFormater.decodeFormattedString(petrol.getLocalization()))
                .filter(petrol -> !this.isInListIfNotMerge(petrol, querryResult))
                .collect(Collectors.toList());

        newPetrols = newPetrols.stream()
                .map(this::addEntity)
                .collect(Collectors.toList());

        querryResult.addAll(newPetrols);
    }

    public List<PetrolStationDTO> filterStationsWithDistance(List<PetrolStationDTO> querryResult, UserParams userParams) {
        return querryResult.stream()
                .map(this::fetchGeoPositionIfNotExists)
                .filter((petrolStationDTO) -> this.verifyUserDistance(petrolStationDTO, userParams.location, userParams.maxDistance))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<PetrolStation> filterStationsWithDistanceNoDTO(List<PetrolStation> querryResult, UserParams userParams) {
        return querryResult.stream()
                .filter((petrolStationDTO) -> this.verifyUserDistance(petrolStationDTO, userParams.location, userParams.maxDistance))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<PetrolStationDTO> filterStationListWithDistanceSort(List<PetrolStationDTO> querryResult, UserParams userParams) {
        return filterStationsWithDistance(querryResult, userParams).stream()
                .sorted(this::compareDistances)
                .peek(this::attachLastPrice)
                .collect(Collectors.toList());
    }


    public List<PetrolStationDTO> filterListOfPetrolsByUserDistance(List<PetrolStationDTO> querryResult, UserParams userParams) {
        return filterStationsWithDistance(querryResult, userParams).stream()
                .peek(this::attachLastPrice)
                .collect(Collectors.toList());
    }

    public Optional<UserParams> extractUserData(MultiValueMap<String, String> multiValueMap) {
        if(isNull(multiValueMap.getFirst("lat")))
            return Optional.empty();

        var userLocation = getUserLocation(multiValueMap);
        var maxDistance = Double.parseDouble(Objects.requireNonNull(multiValueMap.getFirst("maxDistance")));
        return Optional.of(UserParams.builder()
                .location(userLocation)
                .maxDistance(maxDistance)
                .build());
    }

    @Override
    public void updateEntity(PetrolStationPostDTO petrolStationPostDTO, Long id) {
        var station = postDTOMapper.convertIntoObject(petrolStationPostDTO);

        var fuelTypes = station.getFuelTypes().stream()
                .map(fuelType -> fuelTypeReposit.findByName(fuelType.getName()).orElseThrow())
                .collect(Collectors.toList());
        station.setFuelTypes(fuelTypes);

        var stationRep = repository.findById(id).orElseThrow();
        stationRep.setFuelTypes(fuelTypes);
        stationRep.getPetrolStationStats().setIsCarWash(petrolStationPostDTO.getIsCarWash());
        stationRep.getPetrolStationStats().setIsCompressor(petrolStationPostDTO.getIsCompressor());
        stationRep.getPetrolStationStats().setIsHotDogs(petrolStationPostDTO.getIsHotDogs());
        stationRep.getPetrolStationStats().setIsRestaurant(petrolStationPostDTO.getIsRestaurant());
        stationRep.getPetrolStationStats().setIsWC(petrolStationPostDTO.getIsWC());
        stationRep.getPetrolStationStats().setIsWCFree(petrolStationPostDTO.getIsWCFree());
        stationRep.getPetrolStationStats().setIsSelfService(petrolStationPostDTO.getIsSelfService());


        repository.save(stationRep);
    }

    public boolean isInListIfNotMerge(PetrolStationDTO newPetrol, List<PetrolStationDTO> petrolSatations) {
        var theSamePetrolOpt = petrolSatations.stream()
                .filter(petrol -> this.isTheSameLocation(petrol, newPetrol))
                .findFirst();
        if(theSamePetrolOpt.isEmpty())
            return false;

        var theSamePetrol = theSamePetrolOpt.orElseThrow();
        theSamePetrol.getLocalization().setLat(theSamePetrol.getLocalization().getLat());
        theSamePetrol.getLocalization().set_long(theSamePetrol.getLocalization().get_long());
        return true;
    }

    public boolean  isTheSameLocation(PetrolStationDTO petrolStationDTO, PetrolStationDTO petrolStationDTO2) {
        if (isNull(petrolStationDTO.getLocalization().getFormattedAddress()))
            return false;
        return petrolStationDTO.getLocalization().getFormattedAddress().equals(petrolStationDTO2.getLocalization().getFormattedAddress()) ||
                (petrolStationDTO.getLocalization().get_long().equals(petrolStationDTO2.getLocalization().get_long()) &&
                        petrolStationDTO.getLocalization().getLat().equals(petrolStationDTO2.getLocalization().getLat()));
    }

    int compareDistances(PetrolStationDTO distanceA, PetrolStationDTO distanceB) {
        if (distanceA.getDistance().equals(distanceB.getDistance()))
            return 0;
        else if (distanceA.getDistance() < distanceB.getDistance())
            return -1;
        return 1;
    }

    PetrolStationDTO fetchGeoPositionIfNotExists(PetrolStationDTO petrolStationDTO) {
        try {
            if (isNull(petrolStationDTO.getLocalization().get_long())){
                var location = googleApiService.getLocationOfLocalization(petrolStationDTO.getLocalization());
                petrolStationDTO.getLocalization().set_long(location.get_long().toString());
                petrolStationDTO.getLocalization().setLat(location.getLat().toString());
                var petrolStaionToPersits = repository.findById(petrolStationDTO.getId()).orElseThrow();
                marshallAddressData(petrolStaionToPersits.getLocalization());

                localizationCacher.cacheLocalizationInfo(petrolStaionToPersits.getLocalization(), location);
            }
        } catch (InterruptedException | ApiException | IOException e) {
            e.printStackTrace();
        }
        return petrolStationDTO;
    }

    PetrolStation fetchGeoPositionIfNotExists(PetrolStation petrolStationDTO) {
        try {
            if (isNull(petrolStationDTO.getLocalization().get_long())){
                var location = googleApiService.getLocationOfLocalization(petrolStationDTO.getLocalization());
                petrolStationDTO.getLocalization().set_long(location.get_long().toString());
                petrolStationDTO.getLocalization().setLat(location.getLat().toString());
            }
        } catch (InterruptedException | ApiException | IOException e) {
            e.printStackTrace();
        }
        return petrolStationDTO;
    }

    boolean verifyUserDistance(PetrolStationDTO petrolStationDTO, Location userLocation, Double maxDistance) {
        var stationLocation = parseLocalizationDTO(petrolStationDTO.getLocalization());
        var userDistance = GeoCalculator.distance(stationLocation, userLocation);
        if (userDistance < maxDistance) {
           petrolStationDTO.setDistance(userDistance);
           return true;
        }
        return false;
    }

    boolean verifyUserDistance(PetrolStation petrolStation, Location userLocation, Double maxDistance) {
        var localization = localizationDTOMapper.convertIntoDTO(petrolStation.getLocalization());
        var stationLocation = parseLocalizationDTO(localization);
        var userDistance = GeoCalculator.distance(stationLocation, userLocation);
        return userDistance < maxDistance;
    }

    public Location getUserLocation(MultiValueMap<String, String> multiValueMap) {
        var _long = Double.parseDouble(Objects.requireNonNull(multiValueMap.getFirst("long")));
        var lat = Double.parseDouble(Objects.requireNonNull(multiValueMap.getFirst("lat")));

        return Location.builder()
                ._long(_long)
                .lat(lat)
                .build();
    }

    public List<OpinionDTO> getLatestOpinionsOfPetrolStation(Long id) {
        return opinionRpository.findAllByPetrolStationId(id).stream()
                .map(opinionDTOMapper::convertIntoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PetrolStationDTO getEntityById(Long id) {
        var petrolStationDTO = super.getEntityById(id);
        attachLastPrice(petrolStationDTO);
        return petrolStationDTO;
    }

    public void attachLastPrice(PetrolStationDTO petrolStationDTO) {
        var lastFuelPrices = getLastPricesOfFuelsForPetrolStation(petrolStationDTO);
        petrolStationDTO.setFuelPriceDTO(lastFuelPrices);
    }

    @Data
    @Builder
    static class DistanceAndPetrolDTO {
        Location location;
        PetrolStationDTO petrolStationDTO;
    }
}
