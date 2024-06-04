package com.myPokeGame.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "t_profile_photo")
public class ProfilePhoto extends BaseEntity{

    @TableField("user_id")
    Long userId;

    @TableField("profile_photo_suffix")
    String profilePhotoSuffix;

    @TableField("profile_photo_url")
    String profilePhotoUrl;

    @TableField("md5")
    String md5;
}
