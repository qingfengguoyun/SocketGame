package com.myPokeGame.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.myPokeGame.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SocketIoService {

    static ConcurrentHashMap<String, SocketIOClient> clients=new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, String> users=new ConcurrentHashMap<>();
    static ConcurrentHashMap<User,SocketIoService> userss=new ConcurrentHashMap<>();
    @Autowired
    private SocketIOServer socketIOServer;

    @PostConstruct
    private void autoStart(){
        start();
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

    private void start() {

        socketIOServer.addConnectListener(client -> {

            Map<String, List<String>> urlParams = client.getHandshakeData().getUrlParams();
            if(!ObjectUtils.isEmpty(urlParams.getOrDefault("userName",null))){

                List<String> userNames = urlParams.get("userName");
                String userName=userNames.get(0);
                clients.put(client.getSessionId().toString(),client);
                users.put(client.getSessionId().toString(),userName);
                log.info("用户"+userName+"加入");
                socketIOServer.getBroadcastOperations().sendEvent("message","用户"+userName+"加入");
            }
        });

        socketIOServer.addDisconnectListener(client->{
            String id = client.getSessionId().toString();
            clients.remove(id);
            String userName = users.get(id);
            users.remove(id);
            log.info("用户"+userName+"退出");
        });

        socketIOServer.addEventListener("message",String.class,(client,data,ackSender)->{
            String id = client.getSessionId().toString();
            String userName = users.get(id);
            log.info("收到用户"+userName+"发送的消息："+data.toString());
            socketIOServer.getBroadcastOperations().sendEvent("message","用户"+userName+"："+data.toString());
        });
        socketIOServer.start();
    }
}
