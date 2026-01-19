package com.rentalcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import com.rentalcar.config.FptAiProperties;

@SpringBootApplication
@EnableConfigurationProperties(FptAiProperties.class)
public class RentalCarApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(RentalCarApplication.class, args);
        Environment env = ctx.getEnvironment();
        System.out.println("DATASOURCE_URL=" + env.getProperty("spring.datasource.url"));
    }
}
