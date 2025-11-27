package com.hotel.config;

import com.hotel.patterns.behavioral.ReservaSubject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservaSubjectConfig {
    
    @Bean
    public ReservaSubject reservaSubject() {
        return new ReservaSubject();
    }
}

