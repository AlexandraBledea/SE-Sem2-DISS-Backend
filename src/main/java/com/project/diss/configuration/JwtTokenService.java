package com.project.diss.configuration;

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
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class JwtTokenService {

    private static final long EXPIRATIONTIME = Duration.ofHours(4).toMillis();
    private static final String HEADER_STRING = "Authentication";
    private static final String CLAIM_USER = "email";
    private static final String CLAIM_ID = "id";
    private static final String CLAIM_TYPE = "type";
    @Value("${application.secret}")
    private String secret;


    public Authentication getAuthentication(final HttpServletRequest request) throws Exception {
        Authentication auth = null;
        // we still need the old header for various external services calling gfs
        final String token = Optional.ofNullable(request.getHeader(HEADER_STRING)).orElse(null);
        try {
            // use token != null && token.isBlack() if jdk11
            if (token != null && !("").equals(token)) {
                // parse the token.
                Jws<Claims> claims;
                claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(token);
                if (null != claims) {
                    // Check if there is a email present
                    final String email = Optional//
                            .ofNullable(claims.getBody().get(CLAIM_USER))//
                            .map(Object::toString)//
                            .orElseThrow(
                                    () -> new AuthenticationCredentialsNotFoundException("No email given in jwt"));
                    // check type
                    String type = Optional//
                            .ofNullable(claims.getBody().get(CLAIM_TYPE))//
                            .map(Object::toString)//
                            .orElseThrow(
                                    () -> new AuthenticationCredentialsNotFoundException("No role given in jwt"));

                    // [ADMIN] -> ADMIN
                    type = type.replaceAll("\\[|\\]", "");
                    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(type));
                    auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
                }
            }

        } catch (final SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
            log.error("Unsupported jwt token {} with exception {}",
                    token,
                    ex.getMessage());
            throw new Exception(ex);
        } catch (final ExpiredJwtException ex) {
            log.error("Expired jwt token {}",
                    ex.getMessage());
            throw new Exception(ex);
        } catch (final AuthenticationCredentialsNotFoundException ex) {
            log.error("An error occured while trying to create authentication based on jwt token, missing credentials {}",
                    ex.getMessage());
            throw new Exception(ex);
        } catch (final Exception ex) {
            log.error( "Unexpected exception occured while parsing jwt {} exception: {}",
                    token,
                    ex.getMessage());
            throw new Exception(ex);
        }

        log.debug("The authentication constructed by the JwtService");
        return auth;
    }

    public String createJwtToken(final String email, final Type type, final Long id) {
        // create the jwt token
        String jwtToken;

        jwtToken = Jwts.builder()//
                .claim(CLAIM_USER, email)//
                .claim(CLAIM_TYPE, type)//
                .claim(CLAIM_ID, id)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))//
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))//
                .compact();//
        return jwtToken;
    }

}
