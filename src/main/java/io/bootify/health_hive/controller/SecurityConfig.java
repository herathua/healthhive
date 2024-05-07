//package io.bootify.health_hive.controller;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@Configuration
//@EnableWebSecurity
//@ConditionalOnProperty(name = "authorization.enabled", havingValue = "true", matchIfMissing = true)
//@Slf4j
//public class SecurityConfig {
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        log.info("SecurityConfig.filterChain() called");
//
//        http.authorizeHttpRequests(authz -> authz
//                        .requestMatchers(new AntPathRequestMatcher("/actuator/")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/v2/api-docs")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/api-docs/")).permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/**").authenticated()
//                        .requestMatchers(HttpMethod.GET, "/**").authenticated()
//                        .requestMatchers(HttpMethod.POST, "/**").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/**").authenticated()
//                        .anyRequest().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//
//        log.info("RBAC for API removed for testing prometheus.");
//
//        return http.build();
//    }
//}
