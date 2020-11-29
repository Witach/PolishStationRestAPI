package pl.polishstation.polishstationbackend.bootstrap.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@Profile("dev")
public class FakeDate {

        @Autowired
        Random random;

        public LocalDateTime fakeDateOnePastMonth() {
            var now = LocalDateTime.now();
            return LocalDateTime.of(
                    now.getYear(),
                    random.nextInt(6),
                    Math.max(random.nextInt(30), 1),
                    random.nextInt(24),
                    random.nextInt(60),
                    random.nextInt(60)
        );
    }
}
