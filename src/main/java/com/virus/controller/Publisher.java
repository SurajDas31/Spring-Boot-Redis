package com.virus.controller;

import com.virus.model.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Publisher {

    private RedisTemplate redisTemplate;

    private Topic topic;

    public Publisher(RedisTemplate redisTemplate, Topic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @PostMapping("/publish")
    public ResponseEntity publish(@RequestBody Product product){
        redisTemplate.convertAndSend(topic.getTopic(), product.toString());
        return ResponseEntity.ok(product);
    }
}
