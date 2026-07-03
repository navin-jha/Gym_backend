package com.app.demo;

import com.app.demo.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.addInitializers(new DotenvInitializer());
        app.run(args);
    }
}