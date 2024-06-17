package io.bootify.health_hive.config;

import io.bootify.health_hive.domain.User;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        http.cors(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authz) ->
               authz
                       //User
////                       .requestMatchers(HttpMethod.GET, "/file/**").hasRole(USER)
//                       .requestMatchers(HttpMethod.POST, "/**").hasAnyRole(USER)
//                       .requestMatchers(HttpMethod.PUT, "/**").hasAnyRole(USER)
//                       .requestMatchers(HttpMethod.DELETE, "/**").hasAnyRole(USER)
//                       .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole(LAB)
//                       .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole(ADMIN)
//                        .requestMatchers(HttpMethod.POST, "/api/users/**").hasAnyRole(ADMIN,USER)
//                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}/reset-password").hasRole(ADMIN)
//                        //ShareFile
//                        .requestMatchers(HttpMethod.GET, "/api/shareFiles*").hasRole(USER)
//                        .requestMatchers(HttpMethod.POST, "/api/shareFiles*").hasRole(USER)
//                        .requestMatchers(HttpMethod.DELETE, "/api/shareFiles*").hasRole(USER)
//                        //Lab
//                        .requestMatchers(HttpMethod.GET, "/api/labs/{id}").hasAnyRole(ADMIN,LAB)
//                        .requestMatchers(HttpMethod.PUT, "/api/labs/{id}").hasAnyRole(LAB,ADMIN)
//                        .requestMatchers(HttpMethod.POST, "/api/labs*").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.DELETE, "/api/labs/{id}").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.PUT, "/api/labs/{id}/reset-password").hasRole(ADMIN)
//
//                        //Lab Request
//                        .requestMatchers(HttpMethod.GET, "/api/labRequests*").hasAnyRole(LAB,USER)
//                        .requestMatchers(HttpMethod.POST, "/api/labRequests*").hasAnyRole(USER)
//                        .requestMatchers(HttpMethod.DELETE, "/api/labRequests*").hasAnyRole(USER)
//
//                        //lab Report Share
//                        .requestMatchers(HttpMethod.GET, "/api/labReportShares*").hasAnyRole(USER)
//                        .requestMatchers(HttpMethod.POST, "/api/labReportShares*").hasAnyRole(USER)
//                        .requestMatchers(HttpMethod.DELETE, "/api/labReportShares*").hasAnyRole(USER)
//
//                        //Lab Data Upload
//                        .requestMatchers(HttpMethod.GET, "/api/labDataUploads*").hasAnyRole(USER)
//                        .requestMatchers(HttpMethod.POST, "/api/labDataUploads*").hasAnyRole(LAB)
//                        .requestMatchers(HttpMethod.DELETE, "/api/labDataUploads*").hasAnyRole(USER)
//
//                        //File
//                        .requestMatchers(HttpMethod.GET, "/api/files*").hasAnyRole(USER)
//                        .requestMatchers(HttpMethod.POST, "/api/files*").hasAnyRole(LAB)
//                        .requestMatchers(HttpMethod.DELETE, "/api/files*").hasAnyRole(USER)


                        //IPFS
//                        .requestMatchers(HttpMethod.POST, "/file/upload").hasAnyRole(USER,LAB)
//                        .requestMatchers(HttpMethod.GET, "/file/**").hasRole(USER)
                        .anyRequest().hasRole(USER));

        http.sessionManagement(sess -> sess.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

        return http.build();
    }
}