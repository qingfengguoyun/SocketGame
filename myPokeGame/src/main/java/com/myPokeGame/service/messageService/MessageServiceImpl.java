package com.myPokeGame.service.messageService;

import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.UnReadMessage;
import com.myPokeGame.mapper.MessageMapper;
import com.myPokeGame.mapper.UnReadMessageMapper;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.utils.CommonUtils;
import com.myPokeGame.utils.ConvertUtils;
import com.myPokeGame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UnReadMessageMapper unReadMessageMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Message insertMessage(MessagePojo messagePojo) {
        Message message=new Message();
        //TODO：以后替换为JWT，从header中读取
        UserVo userVo = jwtUtils.validateToken();
//        message.setSendUserId(messagePojo.getSendUserId());
        message.setSendUserId(userVo.getUserId());
        message.setContent(messagePojo.getContent());
        message.setDate(new Date());
        message.setReceiveUserId(CommonUtils.getObjectOrNull(messagePojo.getReceiveUserId()));
        message.setReplyMessageId(CommonUtils.getObjectOrNull(messagePojo.getReplyMessageId()));
        message.setIsBroadcast(CommonUtils.getObjectOrNull(messagePojo.getIsBroadcast()));
        messageMapper.insert(message);
        return message;
    }

    @Override
    public Message insertMessage(Message message) {
        messageMapper.insert(message);
        return message;
    }

    @Override
    public List<Message> queryLastestMessages(Integer num) {
        if(ObjectUtils.isEmpty(num)){
            num=5;
        }
        List<Message> messages = messageMapper.queryLatestMessagesByDate(num);
        return messages;
    }

    @Override
    public Message sendPrivateMessage(MessagePojo pojo) {
        Message message = insertMessage(pojo);
        //将消息加入未读消息记录表
        UnReadMessage unReadMessage=new UnReadMessage();
        ConvertUtils.convert(unReadMessage,message);
        unReadMessageMapper.insert(unReadMessage);
        return message;
    }
}
