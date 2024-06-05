package com.myPokeGame.entity;

import com.alibaba.fastjson.annotation.JSONField;

import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
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

    @TableField(value = "user_image_id")
    String userImageId;

    @TableField(updateStrategy = FieldStrategy.IGNORED,value = "user_default_image")
    String userDefaultImage;
}
