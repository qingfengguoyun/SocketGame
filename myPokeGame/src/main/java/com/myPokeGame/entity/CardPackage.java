package com.myPokeGame.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@Builder
@Slf4j
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
        Random random = new Random();
        int index=random.nextInt(this.cardAmount);
        Card rmCard = this.cards.remove(index);
        log.info("取出卡牌："+rmCard.showCard());
        return rmCard;
    }

    public void addCard(Card card){
        if(!this.cards.contains(card)){
            this.cards.add(card);
        }
    }
}
