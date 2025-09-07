package com.hotel.gateway.controllers;


import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties.Reactive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.gateway.models.AuthResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final ReactiveOAuth2AuthorizedClientService authorizedClientService;

    public AuthController(ReactiveOAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    // @GetMapping("/login")   
    // public ResponseEntity<AuthResponse> login(){

    // }

    @GetMapping("/print-token")
    public Mono<String> printToken(Principal principal) {
        return authorizedClientService.loadAuthorizedClient("auth0", principal.getName()).map(oAuth2AuthorizedClient -> {
            OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
            System.out.println("Access Token: " + accessToken.getTokenValue());
            return accessToken.getTokenValue();
        })
        .defaultIfEmpty("No Access Token found");
    }
}
