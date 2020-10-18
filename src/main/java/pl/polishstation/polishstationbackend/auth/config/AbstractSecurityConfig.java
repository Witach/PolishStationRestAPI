package pl.polishstation.polishstationbackend.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.polishstation.polishstationbackend.auth.JwtFilter;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsServiceImpl;

import java.util.List;

public abstract class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final List<String> IGNORED_URLS = List.of("/swagger-resources/**", "/swagger-ui.html", "/v3/api-docs", "/webjars/**", "/swagger-ui/**", "/h2-console/**");

    @Autowired
    protected UserDetailsServiceImpl myUserDetailsService;
    @Autowired
    protected JwtFilter jwtFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http = makeHttpChain(http);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }

    abstract HttpSecurity makeHttpChain(HttpSecurity httpSecurity) throws Exception;
}

