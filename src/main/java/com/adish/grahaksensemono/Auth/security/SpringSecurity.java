package com.adish.grahaksensemono.Auth.security;

import com.adish.grahaksensemono.Auth.Type.Roles;
import com.adish.grahaksensemono.user.service.Impl.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private JwtAuthFilter jwtAuthFilter;

    private UserDetailServiceImpl userDetailService;

    public SpringSecurity(JwtAuthFilter jwtAuthFilter, UserDetailServiceImpl userDetailService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailService = userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                .authenticationProvider(authenticationProvider(passwordEncoder()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/admin/**")
                        .hasRole(Roles.ADMIN.toString())
                        .requestMatchers(
                                "/seller/**",
                                "/product/**"
                        ).hasRole(Roles.SELLER.toString())
                        .requestMatchers("/activity/**")
                        .hasAnyRole(Roles.ANALYST.toString(), Roles.ADMIN.toString())
                        .requestMatchers(
                                "/public/**",
                                "/cart/**"
                        ).hasRole(Roles.USER.toString())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
