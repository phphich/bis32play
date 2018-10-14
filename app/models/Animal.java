package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by COM2-00-PC on 7/12/2018.
 */
@Entity
@Table(name = "tbAnimal")
public class Animal extends Model {
    @Id
    private String id;
    private String  name;
    private double price;
    private  int amount;
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<OrdersDetail> ordersDetailList = new ArrayList<OrdersDetail>();

    public List<OrdersDetail> getOrdersDetailList() {
        return ordersDetailList;
    }

    public Animal() {
    }

    public Animal(String id, String name, double price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static Finder<String, Animal> finder=new Finder<String, Animal>(String.class, Animal.class);
    public static List<Animal> list() {
        return finder.all();
    }
    public static void create(Animal animal){
        animal.save();
    }
    public static void update(Animal animal){
        animal.update();
    }
    public static void delete(Animal animal){
        animal.delete();
    }

}
