package uz.mafia.jwt_aabuo24.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mafia.jwt_aabuo24.entity.Role;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.validity}")
    private Long validity;

    private final UserDetailsService userDetailsService;

    @Bean
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles",roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime())+validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public boolean validateToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        if (claimsJws.getBody().getExpiration().before(new Date())){
            return false;
        }
        return true;
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        }
        return null;
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    private String getUser(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
