package com.myPokeGame.relationEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("t_nfile_tag_relation")
public class NFileTagRelation {

    @TableId(value = "id",type= IdType.ASSIGN_ID)
    Long id;

    @TableField(value = "native_file_id")
    Long NativeFileId;

    @TableField(value = "tag_id")
    Long TagId;

}
