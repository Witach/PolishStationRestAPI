package pl.polishstation.polishstationbackend.domain.user.appuserrole;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserRolesUtils {
    public static List<AppUserRole> listOfAppUserRoles(String... userRoles) {
        return Arrays.stream(userRoles)
                .map(AppUserRole::new)
                .collect(Collectors.toList());
    }
}
