package models;

import com.avaje.ebean.Expr;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by COM2-00-PC on 10/11/2018.
 */
@Entity
@Table(name = "tbUser")
public class User extends Model{
    @Id
    private String id;
    private String name, status;
    private String picture;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Orders> userList = new ArrayList<Orders>();

    public List<Orders> getUserList() {
        return userList;
    }


    public User() {
    }

    public User(String id, String name, String status, String picture, String password) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.picture = picture;
        this.password = password;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Finder<String, User> finder =
             new Finder<String, User>(String.class, User.class);

    public static List<User> list() {
        return finder.all();
    }
    public static void create(User user){
        user.save();
    }
    public static void update(User user){
        user.update();
    }
    public static void delete(User user){
        user.delete();
    }

    public static User authen(String uid, String passwd){
        User user =
                finder.where().and(
                        Expr.eq("id", uid),
                        Expr.eq("password", passwd))
                        .findUnique();
        return user;
    }



}
