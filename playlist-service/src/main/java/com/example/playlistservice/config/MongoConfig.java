package com.example.playlistservice.config;

import com.example.playlistservice.repositories.PlaylistBackupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.playlistservice.repositories",
                        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = PlaylistBackupRepository.class))
public class MongoConfig {
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}
