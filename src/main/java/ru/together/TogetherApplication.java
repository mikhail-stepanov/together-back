package ru.together;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "ru.together")
@PropertySource("classpath:application.properties")
public class TogetherApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TogetherApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
    }

}
