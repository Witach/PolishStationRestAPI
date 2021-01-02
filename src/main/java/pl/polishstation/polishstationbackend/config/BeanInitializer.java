package pl.polishstation.polishstationbackend.config;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polishstation.polishstationbackend.utils.Indexator;

import java.io.FileInputStream;
import java.io.IOException;
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

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("polishstation-323f6-firebase-adminsdk-6qb6p-26f0f73a55.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://polishstation-323f6.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
        return FirebaseMessaging.getInstance();
    }
}
