package com.myPokeGame.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "user")
public class User {

    @TableId(value = "user_id",type= IdType.ASSIGN_ID)
    Long userId;

    @TableField(value = "user_name")
    String userName;

    @TableField(value = "password")
    String password;
}
