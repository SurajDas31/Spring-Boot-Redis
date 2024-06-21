package com.virus.dao;

import com.virus.model.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {

    private static String HASH_KEY = "Product";

    private RedisTemplate redisTemplate;

    public ProductDAO(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Product save(Product product){
        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> getAll(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

}
