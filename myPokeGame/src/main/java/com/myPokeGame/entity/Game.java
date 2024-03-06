package com.myPokeGame.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@TableName(value = "game")
public class Game {

    @TableId(value = "game_id",type = IdType.AUTO)
    Integer gameId;

    @TableField(value = "start_time")
    Date startTime;

    @TableField(value = "end_time")
    Date endTime;

    List<Player> players=new LinkedList<>();

    CardPackage cardPackage;


}
