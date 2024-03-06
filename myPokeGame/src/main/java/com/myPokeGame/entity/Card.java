package com.myPokeGame.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Card {
    Integer value;

    //0:黑桃 1：红桃 2：草花 3：方片
    Integer color;

    public String showCard(){
        switch (this.color){
            case 0:
//                System.out.print(String.format("黑桃：%d ",this.value));
                return String.format("黑桃-%d ",this.value);

            case 1:
//                System.out.print(String.format("红桃：%d ",this.value));
                return String.format("红桃-%d ",this.value);

            case 2:
//                System.out.print(String.format("草花：%d ",this.value));
                return String.format("草花-%d ",this.value);

            case 3:
//                System.out.print(String.format("方片：%d ",this.value));
                return String.format("方片-%d ",this.value);
        }
        return "";
    }
}
