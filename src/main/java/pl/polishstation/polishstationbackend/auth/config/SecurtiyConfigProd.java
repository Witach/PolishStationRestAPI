package pl.polishstation.polishstationbackend.auth.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("prod")
@EnableWebSecurity
public class SecurtiyConfigProd extends AbstractSecurityConfig {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/verify/{token}");
    }

    @Override
    HttpSecurity makeHttpChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests()
//                .antMatchers(IGNORED_URLS.toArray(String[]::new)).permitAll()
//                .antMatchers("/auth").permitAll()
//                .antMatchers("/app-user/register").permitAll()
//                .antMatchers("/verify/{token}").permitAll()
//                .antMatchers("/static/**").permitAll()
//                .antMatchers(HttpMethod.POST,"/app-user").permitAll()
//                .antMatchers(HttpMethod.GET, "/**").authenticated()
//                .antMatchers("/app-user").authenticated()
//                .antMatchers("/app-user-role").authenticated()
//                .anyRequest().authenticated();
        httpSecurity.authorizeRequests().anyRequest().permitAll();
        return httpSecurity;
    }
}
