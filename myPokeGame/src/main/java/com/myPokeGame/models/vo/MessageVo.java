package com.myPokeGame.models.vo;

import com.myPokeGame.entity.Message;
import com.myPokeGame.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageVo {


    User sendUser;

    User receiveUser;

    Long messageId;

    String messageContent;

    Date date;

    public static MessageVo convert(Message message){
        return null;
    }
}
