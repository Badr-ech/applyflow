package com.applyflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class ApplyFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplyFlowApplication.class, args);
    }
}
