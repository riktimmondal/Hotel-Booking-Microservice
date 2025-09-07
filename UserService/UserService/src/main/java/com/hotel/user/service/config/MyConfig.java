package com.hotel.user.service.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.hotel.user.service.config.interceptor.RestTemplateInterceptor;

@Configuration
public class MyConfig {
    
    @Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateInterceptor restTemplateInterceptor) {
		RestTemplate restTemplate =  new RestTemplate();
		restTemplate.setInterceptors(List.of(restTemplateInterceptor));
		return restTemplate;
	}
}
