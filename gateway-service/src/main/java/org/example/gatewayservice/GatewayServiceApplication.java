package org.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("songs-service", r -> r
                        .path("/songs/**", "/playlists/*/songs/**")
                        .uri("http://localhost:8082"))
                // Events routes
                .route("events-service", r -> r
                        .path("/events/**")
                        .uri("http://localhost:8082"))
                // Playlist routes
                .route("playlist-service", r -> r
                        .path("/playlists/**")
                        .and()
                        .not(p -> p.path("/playlists/*/songs/**")) // Exclude the songs path
                        .uri("http://localhost:8081"))
                .build();
    }
}
