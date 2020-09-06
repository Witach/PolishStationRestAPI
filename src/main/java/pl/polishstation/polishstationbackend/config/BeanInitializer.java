package pl.polishstation.polishstationbackend.config;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polishstation.polishstationbackend.utils.Indexator;

import java.util.Locale;
import java.util.Random;

@Profile({"dev", "test"})
@Configuration
public class BeanInitializer {
    @Bean
     public Faker faker(){
        return new Faker(new Locale("pl","PL"));
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public FakeValuesService fakeValuesService() {
        return new FakeValuesService(new Locale("pl-PL"), new RandomService());
    }
}
