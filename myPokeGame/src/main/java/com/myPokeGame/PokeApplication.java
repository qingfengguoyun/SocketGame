package com.myPokeGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PokeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokeApplication.class,args);
    }
}
