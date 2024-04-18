package com.myPokeGame.cotroller;

import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.User;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.MessageVo;
import com.myPokeGame.service.messageService.MessageService;
import com.myPokeGame.service.socketIoService.SocketIoEvents;
import com.myPokeGame.service.socketIoService.SocketIoService;
import com.myPokeGame.service.userService.UserService;
import com.myPokeGame.utils.ConvertUtils;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@Api(tags ="消息接口")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    SocketIoService socketIoService;

    @Autowired
    ConvertUtils convertUtils;

    @Value("${app-env.message-list-default}")
    Integer messageListDefault;


    @ApiOperation(value = "保存信息")
    @PostMapping("/saveMessage")
    public Result saveMessage(@RequestBody MessagePojo messagePojo){
        Message message = messageService.insertMessage(messagePojo);
        return Result.success(message);
    }

    @ApiOperation(value = "查询最新信息")
    @GetMapping("/queryLatestMessages")
    public Result getLatestMessages(@RequestParam(required = false) Integer num){
        num= !ObjectUtils.isEmpty(num)?num:messageListDefault;
        List<Message> messages = messageService.queryLastestMessages(num);
        List<MessageVo> voList=new LinkedList<>();
        voList=convertUtils.convert(messages,new MessageVo());
        return Result.success(voList);
    }

    @ApiOperation(value = "私发消息")
    @PostMapping("/sendPrivateMessage")
    public Result sendPrivateMessage(@RequestBody MessagePojo pojo){
        Message message = messageService.sendPrivateMessage(pojo);
        User sendUser = userService.queryUserById(message.getSendUserId());
        User receiveUser = userService.queryUserById(message.getReceiveUserId());
        MessageVo vo=new MessageVo();
        ConvertUtils.convert(vo,message,sendUser,receiveUser);
        //TODO:socket定向发送给指定用户
        socketIoService.sendGroupMessage(vo, SocketIoEvents.SEND_PRIVATE_MESSAGE, Arrays.asList(pojo.getSendUserId(),pojo.getReceiveUserId()));
        return Result.success(vo);
    }

    @ApiOperation(value = "获取私聊信息")
    @GetMapping("/getPrivateMessage")
    public Result getPrivateMesssageList(Long connectUserId,
                                         @RequestParam(required = false) Integer num){
        num= !ObjectUtils.isEmpty(num)?num:messageListDefault;
        List<Message> messages = messageService.queryLatestPrivteMessages(connectUserId, num);
        List<MessageVo> vos = convertUtils.convert(messages,new MessageVo());
        return Result.success(vos);
    }

}
