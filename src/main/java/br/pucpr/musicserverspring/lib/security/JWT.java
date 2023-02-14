package br.pucpr.musicserverspring.lib.security;

import br.pucpr.musicserverspring.rest.users.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;

public class JWT {
    private static final String SECRET = "D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeT";
    private static final String PREFIX = "Bearer";

    public static Authentication extract(HttpServletRequest req){
        final var header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if(header == null || !header.startsWith(PREFIX)) return null;

        final var token = header.replace(PREFIX, "").trim();
        final var claims = Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .deserializeJsonWith(new JacksonDeserializer(Map.of("user", User.class)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        final var user = claims.get("user", User.class);

        if(user == null) return null;

        final var authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_"+r)).toList();

        return UsernamePasswordAuthenticationToken.authenticated(user, user.getId(), authorities);
    }
}
