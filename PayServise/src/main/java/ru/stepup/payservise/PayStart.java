package ru.stepup.payservise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PayStart {

    public static void main(String[] args) {
//OK        System.getProperties().put( "server.port", 8181 );  // беоем из application.yml
        SpringApplication.run(PayStart.class, args);
    }
}
