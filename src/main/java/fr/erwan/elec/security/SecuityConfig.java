package fr.erwan.elec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecuityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    public SecuityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint,
                          UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {        
        http
            .csrf(crsf -> crsf.disable())
            .addFilterBefore(new UsernamePasswordAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), UsernamePasswordAuthFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/elec/signIn").permitAll()
                    .requestMatchers(HttpMethod.POST, "/elec/jwt").permitAll()
                    .anyRequest().authenticated());   
        return http.build();
    }
}
