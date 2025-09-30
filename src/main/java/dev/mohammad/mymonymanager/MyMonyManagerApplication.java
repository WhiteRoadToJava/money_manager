package dev.mohammad.mymonymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // enalble Email Anotation.
@SpringBootApplication
public class MyMonyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMonyManagerApplication.class, args);
    }

}
