package com.virus.config;

import com.virus.listener.ProductListener;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        return new LettuceConnectionFactory();
    };

//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(){
//        return new JedisConnectionFactory();
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        /*redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());*/
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic("pubsub:product");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        return new MessageListenerAdapter(new ProductListener());
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
//        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory());
        redisMessageListenerContainer.setConnectionFactory(lettuceConnectionFactory());
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter(), channelTopic());
        return redisMessageListenerContainer;
    }

}
