package ru.together;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "ru.together")
public class TogetherApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TogetherApplication.class);
        application.run(args);
    }

}
