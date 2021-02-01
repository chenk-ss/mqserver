package com.chenk.mqprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
@ComponentScan(value = {"com.chenk"})
@EntityScan("com.chenk.mqcommon.*")
@EnableJpaRepositories(basePackages = "com.chenk.mqcommon.repository")
public class MqProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqProviderApplication.class, args);
    }

}
