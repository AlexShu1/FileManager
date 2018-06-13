package com.kylinno.legal.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class FastDFSApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastDFSApplication.class, args);
    }
}