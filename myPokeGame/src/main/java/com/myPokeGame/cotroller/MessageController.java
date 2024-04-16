package com.myPokeGame.cotroller;

import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.User;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.MessageVo;
import com.myPokeGame.service.messageService.MessageService;
import com.myPokeGame.service.userService.UserService;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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


    @ApiOperation(value = "保存信息")
    @PostMapping("/saveMessage")
    public Result saveMessage(@RequestBody MessagePojo messagePojo){
        Message message = messageService.insertMessage(messagePojo);
        return Result.success(message);
    }

    @ApiOperation(value = "查询最新信息")
    @GetMapping("/queryLatestMessages")
    public Result getLatestMessages(@RequestParam(required = false) Integer num){
        List<Message> messages = messageService.queryLastestMessages(num);
        List<MessageVo> voList=new LinkedList<>();
        for(Message mes:messages){
            MessageVo vo=new MessageVo();
            User sendUser = userService.queryUserById(mes.getSendUserId());
            User receiveUser = userService.queryUserById(mes.getReplyUserId());
            vo.setSendUser(sendUser);
            vo.setReceiveUser(receiveUser);
            vo.setMessageContent(mes.getContent());
            vo.setMessageId(mes.getId());
            vo.setDate(mes.getDate());
            voList.add(vo);
        }
        return Result.success(voList);
    }

}
