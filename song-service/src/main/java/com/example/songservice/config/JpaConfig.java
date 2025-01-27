package com.example.songservice.config;

import com.example.songservice.repositories.PlaylistRepository;
import com.example.songservice.repositories.SongRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.songservice.repositories",
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {PlaylistRepository.class, SongRepository.class}))
public class JpaConfig {
}
