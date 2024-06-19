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
@TableName("t_nativefile_source")
public class NativeFileSource extends BaseEntity{

    @TableField("md5")
    String md5;

    @TableField("file_url")
    String fileUrl;

    @TableField("file_preview_url")
    String filePreviewUrl;
}
