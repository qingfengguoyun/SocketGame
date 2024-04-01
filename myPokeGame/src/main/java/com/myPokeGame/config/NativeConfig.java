package com.myPokeGame.config;

import com.myPokeGame.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class NativeConfig {
    //暂时用来记录用户登录状态的map，key：userId，value，登录的时间
    @Bean
    public Map<Long, Date> onlineUserMap(){
        return new HashMap<Long,Date>();
    }
}
