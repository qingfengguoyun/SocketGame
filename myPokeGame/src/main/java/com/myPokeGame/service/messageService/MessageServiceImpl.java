package com.myPokeGame.service.messageService;

import cn.hutool.core.util.ObjUtil;
import com.myPokeGame.entity.Message;
import com.myPokeGame.mapper.MessageMapper;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public Message insertMessage(MessagePojo messagePojo) {
        Message message=new Message();
        //TODO：以后替换为JWT，从header中读取
        message.setSendUserId(messagePojo.getSendUserId());
        message.setContent(messagePojo.getContent());
        message.setDate(new Date());
        message.setReplyUserId(CommonUtils.getObjectOrNull(messagePojo.getReplayUserId()));
        message.setReplyMessageId(CommonUtils.getObjectOrNull(messagePojo.getReplyMessageId()));
        message.setIsBroadcast(true);
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
}
