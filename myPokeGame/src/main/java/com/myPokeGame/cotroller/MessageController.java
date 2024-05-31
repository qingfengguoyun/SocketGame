package com.myPokeGame.cotroller;

import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.User;
import com.myPokeGame.models.dto.UnReadMessageCountDto;
import com.myPokeGame.models.pojo.QueryHistoryPrivateMessagePojo;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.MessageVo;
import com.myPokeGame.models.vo.UnReadMessageCountVo;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.service.messageService.MessageService;
import com.myPokeGame.service.socketIoService.SocketIoEvents;
import com.myPokeGame.service.socketIoService.SocketIoService;
import com.myPokeGame.service.userService.UserService;
import com.myPokeGame.utils.AppEnvConstant;
import com.myPokeGame.utils.ConvertUtils;
import com.myPokeGame.utils.JwtUtils;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    JwtUtils jwtUtils;

    @Value("${app-env.messageListDefaultLength}")
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
        num= !ObjectUtils.isEmpty(num)?num: AppEnvConstant.MESSAGE_LIST_DEFAULT_LENGTH;
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
        num= !ObjectUtils.isEmpty(num)?num:AppEnvConstant.MESSAGE_LIST_DEFAULT_LENGTH;
        List<MessageVo> vos = messageService.queryLatestPrivteMessageVos(connectUserId, num);
//        List<MessageVo> vos = convertUtils.convert(messages,new MessageVo());
        return Result.success(vos);
    }


    @ApiOperation(value = "查询未读私聊消息数量")
    @PostMapping("/queryUnReadMessageCount")
    public Result queryUnReadMessageCount(){
        UserVo userVo = jwtUtils.validateToken();
        List<UnReadMessageCountDto> dtos = messageService.queryUnReadMessageCountByUserId(userVo.getUserId());
        List<UnReadMessageCountVo> vos = ConvertUtils.convert(dtos);
        return Result.success(vos);
    }

    @ApiOperation(value = "根据消息id查询历史消息")
    @GetMapping("/queryHistoryMessagesByMsgId")
    public Result queryHistoryMessagesByMsgId(@RequestParam("msgId") Long msgId){
        List<Message> messages = messageService.queryHistroyMessageByMsgId(msgId);
        List<MessageVo> vos = convertUtils.convert(messages, new MessageVo());
        return Result.success(vos);
    }

    @ApiOperation(value = "根据消息id和用户id查询历史私聊消息")
    @PostMapping("/queryHistoryPrivateMessagesByMsgIdAndUserIds")
    public Result queryHistoryPrivateMessagesByMsgIdAndUserIds(@RequestBody QueryHistoryPrivateMessagePojo pojo){
        List<MessageVo> messageVos = messageService.queryHistoryPrivateMesssgeVosByMsgIdAndUserIds(pojo.getMsgId(), pojo.getUserIds());
        return Result.success(messageVos);
    }
}
