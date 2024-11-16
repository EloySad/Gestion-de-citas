package clinica.riwi.io.gestor_citas.common.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import clinica.riwi.io.gestor_citas.users.domain.Roles;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final String[] PUBLIC_ENDPOINTS = { "/auth/**", "/swagger-ui/**",
                        "/api-docs/v3/**", "/api/**" };
        private final String[] DOCTOR_ENDPOINTS = {};

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtAuthFilter jwtAuthFilter;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                var authenticationProvider = new DaoAuthenticationProvider();

                authenticationProvider.setPasswordEncoder(this.passwordEncoder());
                authenticationProvider.setUserDetailsService(this.userDetailsService);
                return authenticationProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(request -> request
                                                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                                                .requestMatchers(DOCTOR_ENDPOINTS).hasAuthority(Roles.DOCTOR.name())
                                                .anyRequest().authenticated())
                                .authenticationProvider(authenticationProvider())
                                .sessionManagement(sessionManager -> sessionManager
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(this.jwtAuthFilter,
                                                UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

}
