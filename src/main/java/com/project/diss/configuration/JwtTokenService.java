package com.project.diss.configuration;

import com.project.diss.exception.JwtTokenException;
import com.project.diss.persistance.entity.enums.UserType;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class JwtTokenService {

    private static final long EXPIRATION_TIME = Duration.ofMinutes(2).toMillis();
    private static final String HEADER_STRING = "Authorization";
    private static final String CLAIM_USER = "email";
    private static final String CLAIM_ID = "id";
    private static final String CLAIM_TYPE = "type";
    @Value("${application.secret}")
    private String secret;

    public Authentication getAuthentication(final HttpServletRequest request) throws JwtTokenException {
        Authentication auth;

        final String token = request.getHeader(HEADER_STRING);
        if (token == null || token.isEmpty()) {
            log.info("No token found in the request header");
            return null;
        }

        try {

            //Parse the token
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);

            //Checks if there is an email present
            String email = Optional.ofNullable(claims.getBody().get(CLAIM_USER))
                    .map(Object::toString)
                    .orElseThrow(() -> {
                        log.error("JWT token does not contain email");
                        return new AuthenticationCredentialsNotFoundException("No email given in jwt");
                    });

            // Checks if there is a type present and transforms it from e.g. [ADMIN] -> ADMIN
            String type = Optional.ofNullable(claims.getBody().get(CLAIM_TYPE))
                    .map(Object::toString)
                    .orElseThrow(() -> {
                        log.error("JWT token does not contain type");
                        return new AuthenticationCredentialsNotFoundException("No type given in jwt");
                    })
                    .replaceAll("\\[|\\]", "");

            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(type));
            auth = new UsernamePasswordAuthenticationToken(email, null, authorities);

        } catch (final SignatureException | MalformedJwtException | UnsupportedJwtException exception) {
            log.error("Unsupported jwt token {} with exception {}",
                    token,
                    exception.getMessage());
            throw new JwtTokenException();

        } catch (ExpiredJwtException exception) {
            log.error("Expired jwt token {}",
                    exception.getMessage());
            throw new JwtTokenException();

        } catch (final AuthenticationCredentialsNotFoundException ex) {
            log.error("An error occured while trying to create authentication based on jwt token, missing credentials {}",
                    ex.getMessage());
            throw new JwtTokenException();

        } catch (final Exception ex) {
            log.error("Unexpected exception occured while parsing jwt {} exception: {}",
                    token,
                    ex.getMessage());
            throw new JwtTokenException();
        }

        log.debug("The authentication constructed by the JwtService");
        return auth;
    }

    public String createJwtToken(final String email, final UserType type, final Long id) {
        // Create the jwt token
        String jwtToken;

        jwtToken = Jwts.builder()//
                .claim(CLAIM_USER, email)//
                .claim(CLAIM_TYPE, type)//
                .claim(CLAIM_ID, id)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))//
                .compact();//
        return jwtToken;
    }

}
