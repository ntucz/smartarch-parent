package com.smartarch.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {
	
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users_route", r -> r.path("/users/**","/roles/**","/opersets/**")
                .uri("lb://user-service"))
                .build();
    }

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
