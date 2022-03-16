package uz.mafia.jwt_aabuo24.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import uz.mafia.jwt_aabuo24.security.JwtConfigurer;
import uz.mafia.jwt_aabuo24.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .exceptionHandling()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/test").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
