package com.myPokeGame.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName(value = "message")
public class Message {

    @TableId(value = "message_id",type= IdType.ASSIGN_ID)
    Long messageId;
    // 消息内容
    @TableId(value = "content")
    String content;
    // 发送者Id
    @TableId(value = "send_user_id")
    Long sendUserId;
    // 接收者id 默认为null
    @TableId(value = "reply_user_id")
    Long replyUserId;
    // 创建日期
    @TableId(value = "date")
    Date date=new Date();

    //额外的属性
    //是否全员可见
    @TableId(value = "is_broadcast")
    Boolean isBroadcast=true;
    //回复的消息id
    @TableId(value = "reply_message_id")
    Long replyMessageId=null;
}
