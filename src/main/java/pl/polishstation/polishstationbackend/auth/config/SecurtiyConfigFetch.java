package pl.polishstation.polishstationbackend.auth.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("fetch")
@EnableWebSecurity
public class SecurtiyConfigFetch extends AbstractSecurityConfig {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/verify/{token}");
    }

    @Override
    HttpSecurity makeHttpChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().anyRequest().permitAll();
        return httpSecurity;
    }
}
