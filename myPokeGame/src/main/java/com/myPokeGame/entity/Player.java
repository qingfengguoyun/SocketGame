package com.myPokeGame.entity;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Player extends User{


    List<Card> cards;

    //标识连接状态
    Boolean isConnected;

    //是否完成操作
    Boolean isFinish;


    public Player(User user) {
        this.id=user.getId();
        this.name=user.getName();
        this.cards=new LinkedList<>();
        this.isConnected=true;
        this.isFinish=false;
    }

    public void finish(){
        this.isFinish=true;
    }


}
