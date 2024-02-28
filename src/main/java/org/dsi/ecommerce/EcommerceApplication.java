package org.dsi.ecommerce;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
        System.out.println("Now!!");
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
