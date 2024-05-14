package com.imgServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ImgApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImgApplication.class,args);
    }
}
