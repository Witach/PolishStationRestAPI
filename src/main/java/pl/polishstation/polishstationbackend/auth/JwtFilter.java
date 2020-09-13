package pl.polishstation.polishstationbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static pl.polishstation.polishstationbackend.auth.JwtUtils.validateToken;
import static pl.polishstation.polishstationbackend.utils.JsonWebTokens.authTokenDataInstance;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String BEARER_TAG = "Bearer ";

    @Autowired
    private UserDetailsServiceImpl userDetailsServicer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader(AUTH_HEADER_NAME))
                .filter(this::isStartsWithBearer)
                .map(FileterAuthData::new)
                .ifPresent(
                        fileterAuthData -> decodeToken(request, fileterAuthData)
                );
        filterChain.doFilter(request, response);
    }

    private void decodeToken(HttpServletRequest request, FileterAuthData fileterAuthData) {
        var userDetails = this.userDetailsServicer.loadUserByUsername(fileterAuthData.getUsername());
        if (validateToken(fileterAuthData.getJwt(), userDetails)) {
            var authToken = authTokenDataInstance(userDetails);
            authToken.setDetails(detailseSourceInstance(request));
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        }
    }

    private WebAuthenticationDetails detailseSourceInstance(HttpServletRequest request) {
        return new WebAuthenticationDetailsSource().buildDetails(request);
    }

    private boolean isStartsWithBearer(String jwt) {
        return Objects.nonNull(jwt) &&
                jwt.startsWith(BEARER_TAG) &&
                SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
