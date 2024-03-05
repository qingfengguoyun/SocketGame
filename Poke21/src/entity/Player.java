package entity;

import java.util.List;

public class Player implements PokeAction{

    Integer id;
    String name;
    List<Integer> cards;

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
    public void addCard(Integer num) {
        this.cards.add(num);
    }

    @Override
    public boolean checkCardAmount() {
        return this.cards.size()<=4?true:false;
    }

    @Override
    public Integer getSum() {
        Integer sum=0;
        for(Integer i:cards){
            sum+=i;
        }
        return sum;
    }
}
