package com.lavalliere.daniel.spring.reactive_mongo.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

import static java.util.Collections.singletonList;

// Moved to application.properties so no longer required
// @Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Override
    public String getDatabaseName() {
        return "sgf";
    }

    /*
    // Only required if you are using authentication on MongoDB
    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.credential(MongoCredential.createCredential("root",
                "admin", "example".toCharArray()))
            .applyToClusterSettings(settings -> {
                settings.hosts((singletonList(
                    new ServerAddress("127.0.0.1", 27017)
                )));
            });
    }

     */
}
