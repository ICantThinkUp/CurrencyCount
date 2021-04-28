package com.alfabank_test.demo.config;

import interceptors.GifRequestInterceptor;
import org.springframework.context.annotation.Bean;

public class AppConfig {
    @Bean
    public GifRequestInterceptor gifRequestInterceptor() {
        return new GifRequestInterceptor();
    }
}
