package io.bootify.health_hive.config;

import io.bootify.health_hive.domain.User;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String LAB = "lab";
    public static final String TEMP = "temp";
    private final JwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.authorizeHttpRequests(authz -> authz
//                // User
//                .requestMatchers(HttpMethod.GET, "/file/**").hasRole(USER)
//                .requestMatchers(HttpMethod.POST, "/api/users/email/**").hasAnyRole(USER)
//                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole(USER)
//                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole(ADMIN)
//                .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole(USER, LAB)
//                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole(USER)
//                .requestMatchers(HttpMethod.POST, "/api/users/**").hasAnyRole(ADMIN, USER)
//                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole(ADMIN)
//                .requestMatchers(HttpMethod.PUT, "/api/users/{id}/reset-password").hasRole(ADMIN)
//                // ShareFile
//                .requestMatchers(HttpMethod.GET, "/api/shareFiles*").hasRole(USER)
//                .requestMatchers(HttpMethod.POST, "/api/shareFiles*").hasRole(USER)
//                .requestMatchers(HttpMethod.DELETE, "/api/shareFiles*").hasRole(USER)
//                // Lab
//                .requestMatchers(HttpMethod.GET, "/api/labs/email/**").hasAnyRole(ADMIN, LAB)
//                .requestMatchers(HttpMethod.PUT, "/api/labs/{id}").hasAnyRole(LAB, ADMIN)
//                .requestMatchers(HttpMethod.POST, "/api/labs**").hasRole(ADMIN)
//                .requestMatchers(HttpMethod.DELETE, "/api/labs/{id}").hasRole(ADMIN)
//                .requestMatchers(HttpMethod.PUT, "/api/labs/{id}/reset-password").hasRole(ADMIN)
//                // Lab Request
//                .requestMatchers(HttpMethod.GET, "/api/labRequests/lab/{id}").hasAnyRole(LAB, USER)
//                .requestMatchers(HttpMethod.POST, "/api/labRequests*").hasAnyRole(USER)
//                .requestMatchers(HttpMethod.DELETE, "/api/labRequests*").hasAnyRole(USER)
//                // Lab Report Share
//                .requestMatchers(HttpMethod.GET, "/api/labReportShares/**").hasAnyRole(USER)
//                .requestMatchers(HttpMethod.POST, "/api/labReportShares/**").hasAnyRole(USER)
//                .requestMatchers(HttpMethod.DELETE, "/api/labReportShares/**").hasAnyRole(USER)
//                // Lab Data Upload
//                .requestMatchers(HttpMethod.GET, "/api/labDataUploads/**").hasAnyRole(LAB,USER)
//                .requestMatchers(HttpMethod.POST, "/api/labDataUploads/**").hasAnyRole(LAB)
//                .requestMatchers(HttpMethod.DELETE, "/api/labDataUploads/**").hasAnyRole(USER)
//                // File
//                .requestMatchers(HttpMethod.GET, "/api/files/**").hasAnyRole(USER)
//                .requestMatchers(HttpMethod.POST, "/api/files").hasAnyRole(LAB)
//                .requestMatchers(HttpMethod.DELETE, "/api/files/**").hasAnyRole(USER)
//                // IPFS
//                .requestMatchers(HttpMethod.POST, "/api/ipfs/upload").hasAnyRole(USER, LAB)
//                .requestMatchers(HttpMethod.GET, "/api/ipfs/**").hasRole(USER)
//                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/**").permitAll()
//                .anyRequest().denyAll()
                        .anyRequest().permitAll()
        );

        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}