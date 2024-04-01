package com.myPokeGame;

import com.myPokeGame.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PokeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokeApplication.class,args);

    }


}
