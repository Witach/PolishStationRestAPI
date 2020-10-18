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
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.apiutils.filtring.FilterDomainService;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPriceRepository;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.LastFuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTOMapper;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.spec.PetrolStationSpecFactory;
import pl.polishstation.polishstationbackend.externalapis.googleapi.GoogleApiService;
import pl.polishstation.polishstationbackend.utils.geo.GeoCalculator;
import pl.polishstation.polishstationbackend.utils.geo.Location;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public PetrolStationDTO addEntity(PetrolStationPostDTO dto) {
        var petrolStation = postDTOMapper.convertIntoObject(dto);
        var localization = petrolStation.getLocalization();
        localizationRepository.save(localization);
        petrolStation.setLocalization(localization);
        repository.save(petrolStation);
        return mapper.convertIntoDTO(petrolStation);
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

    List<LastFuelPriceDTO> getLastPricesOfFuelsForPetrolStation(PetrolStationDTO petrolStation) {
        return fuelPriceRepository.findLastPricesOfTypeOfPetrolStation(petrolStation.getId()).stream()
                .map(petrolStationDTOMapper::convertIntoLastFuelPriceDTO)
                .collect(Collectors.toList());
    }

    List<PetrolStationDTO> searchForPetrolStations(Sort sort, MultiValueMap<String, String> multiValueMap) throws InterruptedException, ApiException, IOException {
        var querryResult = petrolStationRepository.findAll(petrolStationSpecFactory.specificationFrom(multiValueMap), sort).stream()
                .map(petrolStationDTOMapper::convertIntoDTO)
                .collect(Collectors.toList());

        if(Objects.isNull(multiValueMap.getFirst("lat")))
            return querryResult;

        var userLocation = getUserLocation(multiValueMap);
        var maxDistance = Double.parseDouble(Objects.requireNonNull(multiValueMap.getFirst("maxDistance")));
        if(sort.get().collect(Collectors.toList()).get(0).equals("distance")) {
            return querryResult.stream()
                    .map(this::fetchGeoPositionIfNotExists)
                    .filter((petrolStationDTO) -> this.verifyUserDistance(petrolStationDTO, userLocation, maxDistance))
                    .sorted(this::compareDistances)
                    .collect(Collectors.toList());
        }

        return querryResult.stream()
                .map(this::fetchGeoPositionIfNotExists)
                .filter((petrolStationDTO) -> this.verifyUserDistance(petrolStationDTO, userLocation, maxDistance))
                .collect(Collectors.toList());
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
            if (Objects.isNull(petrolStationDTO.getLocalization().get_long())){
                var location = googleApiService.getLocationOfLocalization(petrolStationDTO.getLocalization());
                petrolStationDTO.getLocalization().set_long(location.get_long().toString());
                petrolStationDTO.getLocalization().setLat(location.getLat().toString());
                var petrolStaionToPersits = repository.findById(petrolStationDTO.getId()).orElseThrow();
                repository.save(petrolStaionToPersits);
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

    private Location getUserLocation(MultiValueMap<String, String> multiValueMap) {
        var _long = Double.parseDouble(Objects.requireNonNull(multiValueMap.getFirst("long")));
        var lat = Double.parseDouble(Objects.requireNonNull(multiValueMap.getFirst("lat")));

        return Location.builder()
                ._long(_long)
                .lat(lat)
                .build();
    }

    @Data
    @Builder
    static class DistanceAndPetrolDTO {
        Location location;
        PetrolStationDTO petrolStationDTO;
    }
}
