package com.myPokeGame.utils;

import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.UnReadMessage;
import com.myPokeGame.entity.User;
import com.myPokeGame.mapper.MessageMapper;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.models.dto.UnReadMessageCountDto;
import com.myPokeGame.models.vo.MessageVo;
import com.myPokeGame.models.vo.UnReadMessageCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Component
public class ConvertUtils {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    public static void convert(Message message,UnReadMessage unReadMessage){
        unReadMessage.setMessageId(message.getId());
        unReadMessage.setDate(message.getDate());
        unReadMessage.setReceiveUserId(message.getReceiveUserId());
        unReadMessage.setSendUserId(message.getSendUserId());
    }

    public static void convert(MessageVo vo, Message mes, User sendUser, User receiveUser){
        vo.setSendUser(sendUser);
        vo.setReceiveUser(receiveUser);
        vo.setMessageContent(mes.getContent());
        vo.setMessageId(mes.getId());
        vo.setDate(mes.getDate());
    }

    /**
     * 转化方法
     * @param messages
     * @param ins 用于标识待转化的类型
     * @return
     */
    public List<MessageVo> convert(Collection<Message> messages,MessageVo ins){
        List<MessageVo> vos=new LinkedList<>();
        Map<Long,User> userMap=new HashMap<>();
        for(Message message:messages){
            MessageVo vo=new MessageVo();
            Long sendUserId = message.getSendUserId();
            Long receiveUserId = message.getReceiveUserId();
            vo.setSendUser(getUserFromMap(sendUserId,userMap));
            vo.setReceiveUser(getUserFromMap(receiveUserId,userMap));
            vo.setDate(message.getDate());
            vo.setMessageId(message.getId());
            vo.setMessageContent(message.getContent());
            vos.add(vo);
        }
        userMap.clear();
        return vos;
    }

    public User getUserFromMap(Long id,Map<Long,User> userMap){
        if(!ObjectUtils.isEmpty(id)){
            if(ObjectUtils.isEmpty(userMap.get(id))){
                User user = userMapper.selectById(id);
                userMap.put(id,user);
            }
        }
        return userMap.get(id);
    }

    public static List<UnReadMessageCountVo> convert(List<UnReadMessageCountDto> dtos){
        List<UnReadMessageCountVo> list=new LinkedList<>();
        for(UnReadMessageCountDto dto:dtos){
            UnReadMessageCountVo vo=new UnReadMessageCountVo();
            vo.setId(dto.getId());
            vo.setUserName(dto.getUserName());
            vo.setUnReadMessageCount(dto.getUnReadMessageCount());
            list.add(vo);
        }
        return list;
    }
}
