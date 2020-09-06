package pl.polishstation.polishstationbackend.domain.user.appuserrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {
    void deleteAppUserRoleByNameEquals(String name);

    @Query("select role from AppUserRole role where role.name = 'USER' ")
    Optional<AppUserRole> getDefaultUserRole();
}
