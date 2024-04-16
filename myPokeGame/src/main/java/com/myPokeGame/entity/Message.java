package com.myPokeGame.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName(value = "message")
public class Message extends BaseEntity{

    // 消息内容
    @TableField(value = "content")
    String content;
    // 发送者Id
    @TableField(value = "send_user_id")
    Long sendUserId;
    // 接收者id 默认为null
    @TableField(value = "reply_user_id")
    Long replyUserId;


    //额外的属性
    //是否全员可见
    @TableField(value = "is_broadcast")
    Boolean isBroadcast=true;
    //回复的消息id
    @TableField(value = "reply_message_id")
    Long replyMessageId=null;
}
