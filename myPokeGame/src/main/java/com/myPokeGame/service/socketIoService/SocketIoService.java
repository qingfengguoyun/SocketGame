package com.myPokeGame.service.socketIoService;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.myPokeGame.entity.User;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.MessageVo;
import com.myPokeGame.service.userService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SocketIoService {

    //key：sessionId，value：SocketIOClient
    static ConcurrentHashMap<String, SocketIOClient> clients=new ConcurrentHashMap<>();
    //key：sessionId，value：User
    static ConcurrentHashMap<String, User> sessionId_user=new ConcurrentHashMap<>();
    static ConcurrentHashMap<Long, SocketIOClient> userId_client=new ConcurrentHashMap<>();
//    static ConcurrentHashMap<User,SocketIOClient> userss=new ConcurrentHashMap<>();
    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private UserService userService;

    @Autowired
    Map<Long, Date> onlineUserMap;

    @PostConstruct
    private void autoStart(){
        log.info("socketIo auto start");
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

                String userName=urlParams.get("userName").get(0);
                Long userId=Long.parseLong(urlParams.get("id").get(0));
                String password=urlParams.get("password").get(0);
                User user=User.builder().userId(userId).userName(userName).password(password).build();

                //可以添加顶号检测，若userId_client的keySet中发现已存在userId，则向该client发送被顶号消息，并移除client
                //添加socket连接记录
                clients.put(client.getSessionId().toString(),client);
                sessionId_user.put(client.getSessionId().toString(),user);
                userId_client.put(user.getUserId(),client);
                log.info("用户"+userName+"加入");
                socketIOServer.getBroadcastOperations().sendEvent("message","用户"+userName+"加入");
            }
        });

        socketIOServer.addDisconnectListener(client->{
            String id = client.getSessionId().toString();
            clients.remove(id);
            String userName = sessionId_user.get(id).getUserName();
            Long userId=sessionId_user.get(id).getUserId();

            sessionId_user.remove(id);
            userId_client.remove(userId);
            onlineUserMap.remove(userId);
            log.info("用户"+userName+"退出");
        });

        socketIOServer.addEventListener(SocketIoEvents.RECEIVE_MESSAGE, JSONObject.class,(client, data, ackSender)->{
            String id = client.getSessionId().toString();
            String userName = sessionId_user.get(id).getUserName();

            MessagePojo pojo = JSONObject.toJavaObject((JSONObject)data, MessagePojo.class);
            log.info("收到用户"+userName+"发送的消息："+pojo.getContent());
            User sender = userService.queryUserById(sessionId_user.get(id).getUserId());
            User receiver=null;
            if(!ObjectUtils.isEmpty(pojo.getReplayUserId())){
                receiver = userService.queryUserById(pojo.getReplayUserId());
            }
            MessageVo vo = MessageVo.builder().sendUser(sender)
                    .receiveUser(receiver)
                    .messageContent(pojo.getContent())
                    .messageId(IdUtil.getSnowflakeNextId())
                    .date(new Date())
                    .build();
//            socketIOServer.getBroadcastOperations().sendEvent(SocketIoEvents.SEND_MESSAGE,"用户"+userName+"："+data.toString());
            sendBroadCastMessage(vo,SocketIoEvents.SEND_MESSAGE);
        });
        socketIOServer.start();
    }

    public void sendBroadCastMessage(Object data,String eventName){
        if(ObjectUtils.isEmpty(data)){
            log.info("推送内容为空");
            return;
        }
        socketIOServer.getBroadcastOperations().sendEvent(eventName, JSON.toJSONString(data));
    }
}
