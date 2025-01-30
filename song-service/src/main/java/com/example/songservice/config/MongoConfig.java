package com.example.songservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.songservice.repositories")
public class MongoConfig {
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}
