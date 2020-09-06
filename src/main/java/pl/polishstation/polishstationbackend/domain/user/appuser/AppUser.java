package pl.polishstation.polishstationbackend.domain.user.appuser;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;
import pl.polishstation.polishstationbackend.domain.user.verification.VerificationToken;
import pl.polishstation.polishstationbackend.entity.BasicEntity;

import javax.persistence.*;
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

    @Column(unique = true, length = 32, nullable = false)
    private String username;

    @Column(unique = true, length = 32, nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    //revert

    @OneToOne(mappedBy = "appUser")
    private VerificationToken verificationToken;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "appUsers")
    @Singular private List<AppUserRole> appUserRoles = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    @Singular private List<FuelPrice> fuelPrices = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    private List<Opinion> opinion = new LinkedList<>();

    public List<AppUserRole> addRole(AppUserRole appUserRole) {
        appUserRoles.add(appUserRole);
        appUserRole.getAppUsers().add(this);
        return appUserRoles;
    }
}
