package com.myPokeGame.utils;

import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.UnReadMessage;
import com.myPokeGame.entity.User;
import com.myPokeGame.models.vo.MessageVo;

public class ConvertUtils {

    public static void convert(UnReadMessage unReadMessage,Message message){
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
}
