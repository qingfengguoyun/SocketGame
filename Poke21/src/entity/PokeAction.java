package entity;

import java.util.LinkedList;
import java.util.List;

public interface PokeAction {

    public void addCard(PokeCard card);

    public boolean checkCardAmount();

    public Integer getSum();

    public void showCards();
}
