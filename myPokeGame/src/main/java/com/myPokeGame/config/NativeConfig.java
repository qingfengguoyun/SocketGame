package com.myPokeGame.config;

import com.myPokeGame.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class NativeConfig {
    //暂时用来记录用户登录状态的map，key：userId，value，登录的时间
    @Bean
    public Map<Long, Date> onlineUserMap(){
        return new HashMap<Long,Date>();
    }

    @Value("${app-env.fileStorage}")
    private String fileStorage;

    @Value("${app-env.filePreviewStorage}")
    private String filePreviewStorage;

    @Value("${app-env.profilePhotoStorage}")
    private String profilePhotoStorage;

    @PostConstruct
    public void initConfig(){
        File file=new File(fileStorage);
        if(file.exists()){
            log.info("文件仓库已存在");
        }
        log.info(file.isDirectory()+"");
        if(!file.exists()){
            try {
                log.info("初始化文件仓库");
                boolean mkdirs = file.mkdirs();
                log.info("文件仓库初始化成功");
            }catch(Exception e){
                log.error("文件仓库初始化失败");
            }
        }

        file=new File(filePreviewStorage);
        if(file.exists()){
            log.info("文件预览仓库已存在");
        }
        log.info(file.isDirectory()+"");
        if(!file.exists()){
            try {
                log.info("初始化文件预览仓库");
                boolean mkdirs = file.mkdirs();
                log.info("文件预览仓库初始化成功");
            }catch(Exception e){
                log.error("文件预览仓库初始化失败");
            }
        }

        file=new File(profilePhotoStorage);
        if(file.exists()){
            log.info("用户头像仓库已存在");
        }
        log.info(file.isDirectory()+"");
        if(!file.exists()){
            try {
                log.info("初始化用户头像仓库");
                boolean mkdirs = file.mkdirs();
                log.info("用户头像仓库初始化成功");
            }catch(Exception e){
                log.error("用户头像仓库初始化失败");
            }
        }
    }
}
