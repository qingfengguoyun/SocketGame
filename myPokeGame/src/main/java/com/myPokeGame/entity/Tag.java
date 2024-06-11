package com.myPokeGame.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName(value = "t_tag")
public class Tag extends BaseEntity {

    @TableField(value = "tag_name")
    String tagName;

    @TableField(value = "is_basic")
    Boolean isBasic=true;

}
