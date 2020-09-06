package pl.polishstation.polishstationbackend.bootstrap.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Profile("dev")
public class OpinionFakeData {

    @Autowired
    Random random;

    @Autowired
    FakeDate fakeDate;


    public List<Opinion> fakeOpinions(List<AppUser> appUsers) {
        return appUsers.stream()
                .map(this::fakeOpinionsWithUserBounded)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Opinion> fakeOpinionsWithUserBounded(AppUser appUser) {
        var opinionsWithoutUser = IntStream.range(0, 20)
                .mapToObj(i -> fakeOpinion(appUser))
                .collect(Collectors.toList());
        appUser.setOpinion(opinionsWithoutUser);
        return opinionsWithoutUser;
    }

    public Opinion fakeOpinion(AppUser appUser) {
        return Opinion.builder()
                .user(appUser)
                .date(fakeDate.fakeDateOnePastMonth())
                .mark(random.nextInt(5))
                .build();
    }
}
