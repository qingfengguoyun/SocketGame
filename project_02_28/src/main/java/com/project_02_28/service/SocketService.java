package com.project_02_28.service;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Slf4j
public class SocketService {

    @Autowired
    private SocketIOServer socketIOServer;

    @PostConstruct
    public void startService(){
        socketIoStart();
    }
    public void socketIoStart(){

    }

    @PreDestroy
    private void autoStop(){
        stop();
    }

    private void stop() {
        if(socketIOServer!=null){
            socketIOServer.stop();
            socketIOServer=null;
        }
    }
}
