package com.myPokeGame.config;

import com.myPokeGame.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class NativeConfig {
    @Bean
    public Map<User, Date> onlineUserMap(){
        return new HashMap<User,Date>();
    }
}
