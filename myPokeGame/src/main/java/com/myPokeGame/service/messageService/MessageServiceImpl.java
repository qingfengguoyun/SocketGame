package com.myPokeGame.service.messageService;

import cn.hutool.core.util.ObjUtil;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UnReadMessageMapper unReadMessageMapper;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 服务层所有包含数据库增删改操作的方法均需添加事务处理：@Transactional
     * @param messagePojo
     * @return
     */
    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public Message sendPrivateMessage(MessagePojo pojo) {
        Message message = insertMessage(pojo);
        //将消息加入未读消息记录表
        UnReadMessage unReadMessage=new UnReadMessage();
        ConvertUtils.convert(unReadMessage,message);
        unReadMessageMapper.insert(unReadMessage);
        return message;
    }

    @Override
    @Transactional
    public List<Message> queryLatestPrivteMessages(Long connectUserId, Integer num) {

        //TODO:先找发送者为联系对象，接收者为自身的最早的未读记录，获得其生成时间
        UserVo userVo = jwtUtils.validateToken();
        UnReadMessage eldestUnReadMessage = unReadMessageMapper.queryBySendUserIdAndReceiveUserId(connectUserId, userVo.getUserId());
        List<Message> messages=new LinkedList<>();
        //如果存在未读消息执行以下
        if(!ObjectUtils.isEmpty(eldestUnReadMessage)){
            //TODO:从消息表中获取在最早未读记录生成时间后的消息
            messages = messageMapper.queryPrivateMessagesByUnReadMessageId(connectUserId, userVo.getUserId(), eldestUnReadMessage.getMessageId(),num);
        }
        else{
            messages = messageMapper.queryPrivateMessages(connectUserId, userVo.getUserId(),num);
        }

        return messages;
    }
}
