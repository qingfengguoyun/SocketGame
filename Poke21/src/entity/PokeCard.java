package entity;

public class PokeCard {

    Integer value;

    //0:黑桃 1：红桃 2：草花 3：方片
    Integer color;

    public PokeCard() {
    }

    public PokeCard(Integer value, Integer color) {
        this.value = value;
        this.color = color;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
