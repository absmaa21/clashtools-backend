package at.htlkaindorf.clashtoolsbackend.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for caching.
 * This class configures Redis as the cache provider with TTL settings for different caches.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Cache manager bean that configures Redis as the cache provider.
     * This bean sets up different TTL values for different caches to ensure data freshness.
     *
     * @param redisConnectionFactory The Redis connection factory
     * @return The configured cache manager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Default cache configuration with TTL of 10 minutes
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()
                        )
                );

        // Specific cache configurations with different TTL values
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // Cache for all base entities with TTL of 30 minutes
        cacheConfigurations.put("baseEntities",
                defaultConfig.entryTtl(Duration.ofMinutes(30)));

        // Cache for individual base entities with TTL of 1 hour
        cacheConfigurations.put("baseEntity",
                defaultConfig.entryTtl(Duration.ofHours(1)));

        // Cache for base entities by category with TTL of 15 minutes
        cacheConfigurations.put("baseEntitiesByCategory",
                defaultConfig.entryTtl(Duration.ofMinutes(15)));

        // Cache for base entities by name with TTL of 15 minutes
        cacheConfigurations.put("baseEntitiesByName",
                defaultConfig.entryTtl(Duration.ofMinutes(15)));

        // Build and return the cache manager
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
