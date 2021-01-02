package pl.polishstation.polishstationbackend.domain.user.appuser;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.user.appuser.validation.IsDefault;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;
import pl.polishstation.polishstationbackend.domain.user.verification.VerificationToken;
import pl.polishstation.polishstationbackend.entity.BasicEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Table(name = "app_user")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class AppUser extends BasicEntity {

    @NotBlank
    @Size(min = 3, max = 32)
    @Column(unique = true, length = 32, nullable = false)
    private String username;

    @NotBlank
    @Email
    @Column(unique = true, length = 32, nullable = false)
    private String email;

    @NotBlank
    @Column(name = "user_password", nullable = false)
    private String password;

    @NotNull
    private Boolean isVerified = false;

    private Long amountOfPoints = 0L;

    private Long amountOfCreatedStations = 0L;
    private Long amountOfEditedInformations = 0L;

    @ManyToMany(mappedBy = "lovedUsers")
    List<PetrolStation> lovedStations = new LinkedList<>();

    //revert

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private VerificationToken verificationToken;

    private String firebaseToken;

    @NotNull
    @IsDefault
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "appUsers")
    private List<AppUserRole> appUserRoles = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    @Singular
    private List<FuelPrice> fuelPrices = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    private List<Opinion> opinion = new LinkedList<>();

    public AppUser addRole(AppUserRole appUserRole) {
        appUserRole.getAppUsers().add(this);
        this.appUserRoles.add(appUserRole);
        return this;
    }

    public static class AppUserBuilder {
        private List<AppUserRole> appUserRoles = new LinkedList<>();
        private List<PetrolStation> lovedStations = new LinkedList<>();
    }
}
