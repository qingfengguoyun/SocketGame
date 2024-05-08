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
@TableName(value = "image")
public class Image extends BaseEntity {

    @TableField("image_name")
    String imageName;

    @TableField("uploader_id")
    Long uploaderId;

    /**
     * 图片尾缀
     */
    @TableField("image_suffix")
    String imageSuffix;

    @TableField("image_url")
    String imageUrl;

    @TableField("md5")
    String md5;

}
