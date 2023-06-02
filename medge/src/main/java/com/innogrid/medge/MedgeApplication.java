package com.innogrid.medge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = "com.*")
@Configuration
public class MedgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedgeApplication.class, args);
    }
}

