package com.example.persondata.config;

import com.example.persondata.mapper.Mapper;
import com.example.persondata.mapper.PersonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Mapper getMapper() {
        return new Mapper();
    }

}
