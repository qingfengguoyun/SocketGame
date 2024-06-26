package com.myPokeGame.service.socketIoService;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.User;
import com.myPokeGame.mapper.MessageMapper;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.MessageVo;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.service.messageService.MessageService;
import com.myPokeGame.service.userService.UserService;
import com.myPokeGame.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
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
    MessageMapper messageMapper;

    @Autowired
    MessageService messageService;

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
                User user=User.builder().id(userId).userName(userName).password(password).build();

                //可以添加顶号检测，若userId_client的keySet中发现已存在userId，则向该client发送被顶号消息，并移除client
                //添加socket连接记录
                clients.put(client.getSessionId().toString(),client);
                sessionId_user.put(client.getSessionId().toString(),user);
                userId_client.put(user.getId(),client);
                log.info("用户"+userName+"加入");
                //接通socket时也要将userId加入onlineUserMap
                //前端刷新时会连续触发socket切断与socekt重新连接
                onlineUserMap.put(userId,new Date());
//                socketIOServer.getBroadcastOperations().sendEvent("message","用户"+userName+"加入");
                sendBroadCastMessage(userId,SocketIoEvents.USER_ONLINE);
            }
        });

        socketIOServer.addDisconnectListener(client->{
            String id = client.getSessionId().toString();
            clients.remove(id);
            String userName = sessionId_user.get(id).getUserName();
            Long userId=sessionId_user.get(id).getId();

            sessionId_user.remove(id);
            userId_client.remove(userId);
            onlineUserMap.remove(userId);
            log.info("用户"+userName+"退出");
            sendBroadCastMessage(userId,SocketIoEvents.USER_OFFLINE);
        });

        socketIOServer.addEventListener(SocketIoEvents.RECEIVE_MESSAGE, JSONObject.class,(client, data, ackSender)->{

            Map<String, List<String>> urlParams = client.getHandshakeData().getUrlParams();
            // 根据参数名获取对应的值
            log.info(urlParams.toString());

            String id = client.getSessionId().toString();
            String userName = sessionId_user.get(id).getUserName();
            MessagePojo pojo = JSONObject.toJavaObject((JSONObject)data, MessagePojo.class);
            log.info("收到用户"+userName+"发送的消息："+pojo.getContent());
            Message message = insertMessageAtSocket(pojo);

            User sender = userService.queryUserById(sessionId_user.get(id).getId());
            User receiver=null;
            if(!ObjectUtils.isEmpty(pojo.getReceiveUserId())){
                receiver = userService.queryUserById(pojo.getReceiveUserId());
            }
            MessageVo vo = MessageVo.builder().sendUser(sender)
                    .receiveUser(receiver)
                    .messageContent(message.getContent())
                    .messageId(message.getId())
                    .date(message.getDate())
                    .build();
            sendBroadCastMessage(vo,SocketIoEvents.SEND_MESSAGE);
        });
        socketIOServer.start();
    }

    /**
     * socket 广播方法
     * @param data
     * @param eventName
     */
    public void sendBroadCastMessage(Object data,String eventName){
        if(ObjectUtils.isEmpty(data)){
            log.info("推送内容为空");
            return;
        }
        socketIOServer.getBroadcastOperations().sendEvent(eventName, JSON.toJSONString(data));
    }

    /**
     * socket 定向发送方法
     * @param data
     * @param eventName
     */
    public void sendGroupMessage(Object data, String eventName, Collection<Long> userPks ){
        if(ObjectUtils.isEmpty(data)){
            log.info("推送内容为空");
            return;
        }
        for(Long pk:userPks){
            SocketIOClient client = userId_client.get(pk);
            //client不为null则推送消息
            if(!ObjectUtils.isEmpty(client)){
                client.sendEvent(eventName,JSON.toJSONString(data));
            }
        }
    }

    //socket没有requestHeader，拿不到token (暂时不知道如何获取header）
    @Transactional
    public Message insertMessageAtSocket(MessagePojo messagePojo) {
        Message message=new Message();
        message.setSendUserId(messagePojo.getSendUserId());
        message.setContent(messagePojo.getContent());
        message.setDate(new Date());
        message.setReceiveUserId(CommonUtils.getObjectOrNull(messagePojo.getReceiveUserId()));
        message.setReplyMessageId(CommonUtils.getObjectOrNull(messagePojo.getReplyMessageId()));
        message.setIsBroadcast(!ObjectUtils.isEmpty(messagePojo.getIsBroadcast()?messagePojo.getIsBroadcast():true));
        messageMapper.insert(message);
        return message;
    }
}
