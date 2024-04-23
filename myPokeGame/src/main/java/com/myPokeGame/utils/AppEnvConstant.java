package com.myPokeGame.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * yml文件内配置项读取
 */
@Component
@ConfigurationProperties(prefix = "app-env")
@Data
public class AppEnvConstant {

    public static Integer MESSAGE_LIST_DEFAULT_LENGTH;

    private Integer messageListDefaultLength;

    @PostConstruct
    public void init(){
        MESSAGE_LIST_DEFAULT_LENGTH=this.messageListDefaultLength;
    }

}
