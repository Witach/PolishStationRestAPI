package pl.polishstation.polishstationbackend.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.bootstrap.fake.*;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPriceRepository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.localization.LocalizationRepository;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.opinion.OpinionRpository;
import pl.polishstation.polishstationbackend.domain.petrolstation.PetrolStationRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRoleRepository;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationDTO;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationService;

import static pl.polishstation.polishstationbackend.utils.FilterFunctions.filterAgainstNull;

@Component
@Slf4j
@Profile("dev")
@Order(3)
public class DBFeed implements CommandLineRunner {

    private LocalizationFakeData localizationFakeData;
    private OpinionFakeData opinionFakeData;
    private AppUserFakeData appUserFakeData;
    private FuelPriceFakeData fuelPriceFakeData;
    private PetrolStationFakeData petrolStationFakeData;
    private FuelTypeRepository fuelTypeRepository;
    private FuelPriceRepository fuelPriceRepository;
    private PetrolStationRepository petrolStationRepository;
    private OpinionRpository opinionRpository;
    private AppUserRepository appUserRepository;
    private AppUserRoleRepository appUserRoleRepository;
    private LocalizationRepository localizationRepository;
    private InfrastructureFuelStationService infrastructureFuelStationService;

    @Value("${apis.infrastructure-fuel-station.mock.file}")
    String mockFile;

    @Autowired
    public DBFeed(LocalizationFakeData localizationFakeData, OpinionFakeData opinionFakeData, AppUserFakeData appUserFakeData, FuelPriceFakeData fuelPriceFakeData, PetrolStationFakeData petrolStationFakeData, FuelTypeRepository fuelTypeRepository, FuelPriceRepository fuelPriceRepository, PetrolStationRepository petrolStationRepository, OpinionRpository opinionRpository, AppUserRepository appUserRepository, AppUserRoleRepository appUserRoleRepository, LocalizationRepository localizationRepository, InfrastructureFuelStationService infrastructureFuelStationService) {
        this.localizationFakeData = localizationFakeData;
        this.opinionFakeData = opinionFakeData;
        this.appUserFakeData = appUserFakeData;
        this.fuelPriceFakeData = fuelPriceFakeData;
        this.petrolStationFakeData = petrolStationFakeData;
        this.fuelTypeRepository = fuelTypeRepository;
        this.fuelPriceRepository = fuelPriceRepository;
        this.petrolStationRepository = petrolStationRepository;
        this.opinionRpository = opinionRpository;
        this.appUserRepository = appUserRepository;
        this.appUserRoleRepository = appUserRoleRepository;
        this.localizationRepository = localizationRepository;
        this.infrastructureFuelStationService = infrastructureFuelStationService;
    }

    @Override
    public void run(String... args) {
        var fuelTypes = fuelTypeRepository.saveAll(fuelPriceFakeData.fakeFuelTypes());
        var appUserRoles = appUserRoleRepository.saveAll(appUserFakeData.fakeAppUserRoles());
        var appUsers = appUserRepository.saveAll(appUserFakeData.fakeAppUsers(appUserRoles));
        var fuelPrice = fuelPriceFakeData.fakeFuelPrices(fuelTypes, appUsers);
        var localizations = localizationFakeData.fakeLocalizations();
        var opinions = opinionFakeData.fakeOpinions(appUsers);

        petrolStationRepository.saveAll(petrolStationFakeData.fakePetrolStations(fuelPrice, opinions, localizations));

        opinions = filterAgainstNull(opinions, Opinion::getPetrolStation);
        localizations = filterAgainstNull(localizations, Localization::getPetrolStation);
        fuelPrice = filterAgainstNull(fuelPrice, FuelPrice::getPetrolStation);
        fuelPrice = filterAgainstNull(fuelPrice, FuelPrice::getUser);

        opinionRpository.saveAll(opinions);
        localizationRepository.saveAll(localizations);
        fuelPriceRepository.saveAll(fuelPrice);
//        infrastructureFuelStationService.loadFuelStationsFromFileAndPersist(mockFile);
    }


}
