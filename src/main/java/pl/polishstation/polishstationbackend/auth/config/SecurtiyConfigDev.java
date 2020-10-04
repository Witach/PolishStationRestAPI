package pl.polishstation.polishstationbackend.auth.config;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("dev")
@EnableWebSecurity
public class SecurtiyConfigDev extends AbstractSecurityConfig {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/verify/{token}");
    }

    @Override
    HttpSecurity makeHttpChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/app-user/register").permitAll()
                .antMatchers("/verify/{token}").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers(HttpMethod.POST,"/app-user").permitAll()
                .antMatchers(HttpMethod.GET, "/**").authenticated()
                .antMatchers("/app-user").authenticated()
                .antMatchers("/app-user-role").authenticated()
                .anyRequest().authenticated();
        return httpSecurity;
    }
}
