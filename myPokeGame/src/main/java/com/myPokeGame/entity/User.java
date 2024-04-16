package com.myPokeGame.entity;

import com.alibaba.fastjson.annotation.JSONField;

import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
//存在类继承时需使用SuperBuilder
@SuperBuilder
@TableName(value = "user")
public class User extends BaseEntity{


//    @TableId(value = "user_id",type= IdType.ASSIGN_ID)
////  @JSONField(serializeUsing = ToStringSerializer.class)
//    Long userId;

    @TableField(value = "user_name")
    String userName;

    @TableField(value = "password")
    String password;
}
