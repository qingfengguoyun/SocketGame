package com.myPokeGame.service.messageService;

import cn.hutool.core.util.ObjUtil;
import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.UnReadMessage;
import com.myPokeGame.mapper.MessageMapper;
import com.myPokeGame.mapper.UnReadMessageMapper;
import com.myPokeGame.models.dto.UnReadMessageCountDto;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.MessageVo;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.utils.AppEnvConstant;
import com.myPokeGame.utils.CommonUtils;
import com.myPokeGame.utils.ConvertUtils;
import com.myPokeGame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UnReadMessageMapper unReadMessageMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ConvertUtils convertUtils;

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
            num= AppEnvConstant.MESSAGE_LIST_DEFAULT_LENGTH;
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
        ConvertUtils.convert(message,unReadMessage);
        unReadMessageMapper.insert(unReadMessage);
        return message;
    }

    @Override
    public List<Message> queryHistroyMessageByMsgId(Long msgId) {
        List<Message> messages = messageMapper.queryHistoryMessagesByBottomMessageId(msgId);
        return messages;
    }


    @Transactional
    List<Message> queryLatestPrivteMessages(Long connectUserId, Integer num) {

        //TODO:先找发送者为联系对象，接收者为自身的最早的未读记录，获得其生成时间
        UserVo userVo = jwtUtils.validateToken();
        List<Message> messages=new LinkedList<>();
        messages = messageMapper.queryPrivateMessages(connectUserId, userVo.getUserId(),num);
        return messages;
    }

    @Override
    @Transactional
    public List<MessageVo> queryLatestPrivteMessageVos(Long connectUserId, Integer num) {
        //TODO:先找发送者为联系对象，接收者为自身的最早的未读记录，获得其生成时间
        UserVo userVo = jwtUtils.validateToken();
        UnReadMessage eldestUnReadMessage = unReadMessageMapper.queryBySendUserIdAndReceiveUserId(connectUserId, userVo.getUserId());
        List<Message> messages = messageMapper.queryPrivateMessages(connectUserId, userVo.getUserId(),num);
        List<MessageVo> vos = convertUtils.convert(messages, new MessageVo());
        //依据最早未读消息，给vo添加未读标识
        if(!ObjectUtils.isEmpty(eldestUnReadMessage)){
            vos.stream().forEach(t->{
                //如果消息的时间晚于最早未读消息，且发送者不为自身
                if(t.getDate().getTime()>=eldestUnReadMessage.getDate().getTime()&&t.getSendUser().getId()!=userVo.getUserId()){
                    t.setIsUnRead(true);
                }
            });
        }
        //清除全部发送者为connectUser，接收者为userVo的未读消息记录
        unReadMessageMapper.deleteBySenderIdAndReceiverId(connectUserId,userVo.getUserId());
        return vos;
    }

    @Override
    public List<MessageVo> queryHistoryPrivateMesssgeVosByMsgIdAndUserIds(Long msgId, List<Long> userIds) {

        Map<String,Object> queryMap=new HashMap<>();
        queryMap.put("msgId",msgId);
        queryMap.put("userIds",userIds);
        //mapper层若传参复杂时，需将所有参数封装为Map,其中key为String，value为Object
        List<Message> messages = messageMapper.queryHistoryPrivateMessagesByMessageIdAndUserIds(queryMap);
        List<MessageVo> vos = convertUtils.convert(messages, new MessageVo());
        return vos;
    }

    @Override
    public List<UnReadMessageCountDto> queryUnReadMessageCountByUserId(Long userId) {

        UserVo userVo = jwtUtils.validateToken();
        List<UnReadMessageCountDto> dtos = unReadMessageMapper.queryUnReadMessageCountByUserId(userId);
        return dtos;
    }
}
