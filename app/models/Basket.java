package models;

/**
 * Created by COM2-00-PC on 10/13/2018.
 */
public class Basket {
    private Animal animal;
    private int amount;

    public Basket() {
    }

    public Basket(Animal animal, int amount) {
        this.animal = animal;
        this.amount = amount;
    }


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
