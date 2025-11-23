package edu.unicolombo.trustHotelAPI.infrastructure.security;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.unicolombo.trustHotelAPI.dto.employee.UpdateManagerDTO;
import edu.unicolombo.trustHotelAPI.dto.employee.UpdatePersonnelDTO;
import edu.unicolombo.trustHotelAPI.dto.employee.UpdateReceptionistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private JwtSecurityFilter jwtSecurityFilter;
    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedOrigin("http://127.0.0.1:5501");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(c -> c.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers("/api/v1/employees/**").hasAnyRole("ADMIN", "MANAGER")
                    .requestMatchers("/api/v1/clients/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                    .requestMatchers("/api/v1/rooms/**").hasAnyRole("ADMIN", "MANAGER")
                    .requestMatchers("/api/v1/hotels/**").hasAnyRole("ADMIN", "MANAGER", "RECEPTIONIST")
                    .requestMatchers("/api/v1/bookings/**").hasAnyRole("ADMIN", "RECEPTIONIST")
//                    .requestMatchers("/api/v1/payments/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                .requestMatchers("/api/v1/**").hasRole("ADMIN")

                .requestMatchers("/**").authenticated()
            )
            .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(c -> c.authenticationEntryPoint(
            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder) throws Exception{
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
                authBuilder.userDetailsService(customeUserDetailsService)
                .passwordEncoder(encoder);

        return authBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper= new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerSubtypes(UpdateManagerDTO.class, UpdateReceptionistDTO.class, UpdatePersonnelDTO.class);
        return mapper;
    }
}
