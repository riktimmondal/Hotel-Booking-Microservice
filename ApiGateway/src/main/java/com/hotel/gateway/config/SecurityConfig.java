package com.hotel.gateway.config;

import java.beans.Customizer;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${auth0.audience}")
    private String audience;

    private final ReactiveClientRegistrationRepository respository;

    public SecurityConfig(ReactiveClientRegistrationRepository respository) {
        this.respository = respository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
        .authorizeExchange(authroizeExchangeSpec -> authroizeExchangeSpec
            .anyExchange().authenticated()
        )
        .oauth2Login(oAuthLoginSpec -> oAuthLoginSpec
            .authorizationRequestResolver(
                authorizationRequestResolver(respository)
            )
        )
        .oauth2ResourceServer(oAuth2 -> oAuth2
            .jwt(jwt -> jwt
                .jwtAuthenticationConverter(converter())
            )
        );

        return httpSecurity.build();
    }

    private ServerOAuth2AuthorizationRequestResolver authorizationRequestResolver(ReactiveClientRegistrationRepository respository) {
        DefaultServerOAuth2AuthorizationRequestResolver resolver = new DefaultServerOAuth2AuthorizationRequestResolver(respository);
        resolver.setAuthorizationRequestCustomizer(authBuilderCustomizer());
        return resolver;
    }

    private Consumer<OAuth2AuthorizationRequest.Builder> authBuilderCustomizer() {
        return (customizer) -> {
            customizer.additionalParameters(params -> params.put("audience", audience));
        };
    }

    @Bean
    public ReactiveJwtAuthenticationConverterAdapter converter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> authorities = defaultConverter.convert(jwt);
            Collection<GrantedAuthority> customAuthorities = 
                jwt.getClaimAsStringList("https://hotelservice.com/roles")
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            authorities.addAll(customAuthorities);
            return authorities;
        });
        return new ReactiveJwtAuthenticationConverterAdapter(jwtConverter);
    }
}
