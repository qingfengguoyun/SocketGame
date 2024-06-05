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

    // 用户上传的头像
    @TableField("profile_photo_url")
    String profilePhotoUrl;

    // 头像原始图md5
    @TableField("md5")
    String md5;

    // 用户上传头像的原始图
    @TableField("profile_photo_org_url")
    String profilePhotoOrgUrl;
}
