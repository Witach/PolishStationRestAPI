package pl.polishstation.polishstationbackend.auth.config;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("test")
@EnableWebSecurity
public class SecurityConfigTest extends AbstractSecurityConfig {
    @Override
    HttpSecurity makeHttpChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers( "/auth").permitAll()
                .antMatchers("/app-user/register").permitAll()
                .antMatchers("/app-user/verify/**").permitAll()
                .antMatchers(HttpMethod.POST,"/app-user").permitAll()
                .antMatchers(HttpMethod.GET, "/**").authenticated()
                .antMatchers(HttpMethod.POST, "/auth").authenticated()
                .antMatchers("/app-user").authenticated()
                .antMatchers("/app-user-role").authenticated()
                .anyRequest().authenticated();
        return httpSecurity;
    }
}
