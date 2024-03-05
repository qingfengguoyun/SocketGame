package entity;

import java.util.LinkedList;
import java.util.List;

public class Player implements PokeAction{

    Integer id;
    String name;
    List<PokeCard> cards=new LinkedList<>();

    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addCard(PokeCard card) {
        this.cards.add(card);
        showCards();
    }

    @Override
    public boolean checkCardAmount() {
        return this.cards.size()<=4?true:false;
    }

    @Override
    public Integer getSum() {
        Integer sum=0;
        for(PokeCard i:cards){
            sum+=i.getValue();
        }
        return sum;
    }

    @Override
    public void showCards() {
        System.out.print(String.format("player：%s cards:",this.name));
        for(PokeCard n:this.cards){
            System.out.print(n.getValue()+" ");
        }
        System.out.println();
    }
}
