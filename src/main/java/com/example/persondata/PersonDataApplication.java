package com.example.persondata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PersonDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonDataApplication.class, args);
    }

}
