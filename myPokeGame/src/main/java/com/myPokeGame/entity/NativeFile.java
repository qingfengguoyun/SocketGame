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
@TableName(value = "t_nativefile")
public class NativeFile extends BaseEntity {

    @TableField("file_name")
    String fileName;

    @TableField("uploader_id")
    Long uploaderId;

    /**
     * 文件尾缀
     */
    @TableField("file_suffix")
    String fileSuffix;

    /**
     * 文件具体路径
     */
    @TableField("file_url")
    String fileUrl;

    /**
     * 文件预览图路径
     */
    @TableField("file_preview_url")
    String filePreviewUrl;

    @TableField("file_type")
    String fileType;

    @TableField("md5")
    String md5;
    
}
