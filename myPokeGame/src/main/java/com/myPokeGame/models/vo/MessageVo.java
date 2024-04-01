package com.myPokeGame.models.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageVo {

    Long userId;

    String userName;

    Long messageId;

    String messageContent;
}
