package com.learning.pact.contract.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class EmployeeApplication {
    public static void main(String[] args){
        SpringApplication.run(EmployeeApplication.class);
    }
}
