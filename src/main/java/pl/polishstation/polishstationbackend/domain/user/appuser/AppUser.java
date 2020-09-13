package pl.polishstation.polishstationbackend.domain.user.appuser;

import lombok.*;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.user.appuser.validation.IsDefault;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;
import pl.polishstation.polishstationbackend.domain.user.verification.VerificationToken;
import pl.polishstation.polishstationbackend.entity.BasicEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.LinkedList;
import java.util.List;

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
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    //revert

    @NotNull
    @OneToOne(mappedBy = "appUser")
    private VerificationToken verificationToken;

    @NotNull
    @IsDefault
    @ManyToMany(mappedBy = "appUsers")
    @Singular private List<AppUserRole> appUserRoles = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    @Singular private List<FuelPrice> fuelPrices = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    private List<Opinion> opinion = new LinkedList<>();

    public AppUser addRole(AppUserRole appUserRole) {
        appUserRole.getAppUsers().add(this);
        return this;
    }
}
