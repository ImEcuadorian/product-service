package io.github.imecuadorian.product.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.web.server.*;
import org.springframework.security.web.server.*;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        exchanges -> exchanges.pathMatchers("/**").permitAll().anyExchange().authenticated());
        return http.build();
    }
}
