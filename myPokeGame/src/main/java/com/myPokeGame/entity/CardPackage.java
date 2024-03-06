package com.myPokeGame.entity;

import java.util.LinkedList;
import java.util.List;

public class CardPackage {
    List<Card> cards=new LinkedList<>();
    Integer cardAmount=52;


    public CardPackage() {
        for(int num=1;num<=13;num++){
            for(int color=0;color<4;color++){
                this.cards.add(Card.builder().value(num).color(color).build());
            }
        }
        this.cardAmount=52;
    }

    public Card getCard(){
//        Integer random=
        return null;
    }
}
