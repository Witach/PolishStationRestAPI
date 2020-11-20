package pl.polishstation.polishstationbackend.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsImpl;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;

import javax.persistence.EntityExistsException;

@Aspect
@Component
public class UserPoints {

    @Autowired
    AppUserRepository appUserRepository;

    @After("@annotation(ScorePoints)")
    public void scoreThePoints() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var appUser = appUserRepository.findById(userDetails.getUserId()).orElseThrow(EntityExistsException::new);
        if(appUser.getAmountOfPoints() < 500) {
            appUser.setAmountOfPoints(appUser.getAmountOfPoints() + 5);
            appUserRepository.save(appUser);
        }
    }
}