package pl.polishstation.polishstationbackend.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Profile({"dev","test"})
@Configuration
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class TestConfig {
}
