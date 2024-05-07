package com.imgServer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
//@Builder
//存在类继承时需使用SuperBuilder
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @TableId(value = "id",type= IdType.ASSIGN_ID)
    Long id;

    @TableField(value = "date")
    Date date;

    public BaseEntity(BaseEntity entity){
        this.setId(entity.getId());
        this.setDate(entity.getDate());
    }
}
