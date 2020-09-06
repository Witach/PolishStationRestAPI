package pl.polishstation.polishstationbackend.domain.user.appuserrole;


import lombok.*;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.entity.BasicEntity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "app_role")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
public class AppUserRole extends BasicEntity {

    @Column(unique = true, length = 32, nullable = false)
    private String name;

    //revert

    @JoinTable(
            name = "app_user_app_role",
            joinColumns = { @JoinColumn(name = "app_user_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_role_id") }
    )
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Singular private List<AppUser> appUsers = new LinkedList<>();

    public AppUserRole(String name) {
        this.name = name;
    }
}
