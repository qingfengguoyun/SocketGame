package com.myPokeGame.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "un_read_message")
public class UnReadMessage extends BaseEntity {

    @TableField(value = "send_user_id")
    Long sendUserId;

    @TableField(value = "receive_user_id")
    Long receiveUserId;

    @TableField(value = "message_id")
    Long messageId;
}
