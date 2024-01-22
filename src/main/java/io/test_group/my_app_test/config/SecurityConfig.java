package io.test_group.my_app_test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "authorization.enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("SecurityConfig.filterChain() called");

        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/index.html"))
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/**")
                        .authenticated()
                        // .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/users/**")
                        .authenticated()
                        // .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/**")
                        .authenticated()
                        // .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/**")
                        .authenticated()
                        // .hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        log.info("RBAC for API not implemented. Use a separate realm for API security.");

        return http.build();
    }
}
