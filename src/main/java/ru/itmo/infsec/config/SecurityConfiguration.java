package ru.itmo.infsec.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itmo.infsec.entity.Account;
import ru.itmo.infsec.jwt.JwtFilter;
import ru.itmo.infsec.jwt.JwtManager;
import ru.itmo.infsec.repository.AccountRepository;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtManager jwtManager(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.ttlMs}") long ttlMs
    ) {
        return new JwtManager(secret, ttlMs);
    }

    @Bean
    public JwtFilter jwtFilter(
            JwtManager jwtManager,
            UserDetailsService userDetailsService
    ) {
        return new JwtFilter(
                jwtManager,
                userDetailsService
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter filter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(conf -> conf
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpStatus.UNAUTHORIZED.value())
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpStatus.FORBIDDEN.value())
                        )
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(
            AccountRepository accountRepository
    ) {
        return username -> {
            Account account = accountRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            return User.builder()
                    .username(account.getLogin())
                    .password(account.getPassword())
                    .roles("USER")
                    .build();
        };
    }
}
