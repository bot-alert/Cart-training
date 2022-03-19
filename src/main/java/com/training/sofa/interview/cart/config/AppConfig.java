package com.training.sofa.interview.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.training.sofa.interview.cart.services.CartService;
import com.training.sofa.interview.cart.services.CartStorage;
import com.training.sofa.interview.cart.services.PromotionEngine;

@Configuration
public class AppConfig {

    @Bean
    public CartStorage cartStorage() {
        return new CartStorage();
    }

    @Bean
    public CartService cartService(){
        return  new CartService();
    }

    @Bean
    public PromotionEngine promotionEngine(){
        return new PromotionEngine();
    }

}
