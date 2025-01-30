package org.example.gatewayservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(
            RouteLocatorBuilder builder
    ) {
        return builder.routes()
                .route("songs-service", r -> r
                        .path("/songs/**", "/playlists/*/songs/**")
                        .uri("lb://song-service"))
                // Events routes
                .route("events-service", r -> r
                        .path("/events/**")
                        .uri("lb://song-service"))
                // Playlist routes
                .route("playlist-service", r -> r
                        .path("/playlists/**")
                        .and()
                        .not(p -> p.path("/playlists/*/songs/**")) // Exclude the songs path
                        .uri("lb://playlist-service"))
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfig.addAllowedHeader("*");
        corsConfig.addExposedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}

