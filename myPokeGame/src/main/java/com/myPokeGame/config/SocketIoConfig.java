package com.myPokeGame.config;


import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
//@ConfigurationProperties(prefix = "socketio")
public class SocketIoConfig {

    @Value("${socketio.host}")
    private String host;

    @Value("${socketio.port}")
    private Integer port;

    @Value("${socketio.bossCount}")
    private int bossCount;

    @Value("${socketio.workCount}")
    private int workCount;

    @Value("${socketio.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketio.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketio.pingTimeout}")
    private int pingTimeout;

    @Value("${socketio.pingInterval}")
    private int pingInterval;

    @Bean
    public SocketIOServer socketIOServer(){
//        SocketConfig socketConfig=new SocketConfig();
//        socketConfig.setTcpNoDelay(true);
//        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config =new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);
        config.setAllowCustomRequests(true);
        return new SocketIOServer(config);
    }

}
