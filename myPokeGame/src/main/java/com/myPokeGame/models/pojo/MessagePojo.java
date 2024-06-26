package com.myPokeGame.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagePojo {

    Long sendUserId;

    String content;

    Long replyMessageId=null;

    Long receiveUserId=null;

    Boolean isBroadcast=true;
}
