package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by COM2-00-PC on 9/27/2018.
 */
@Entity
@Table(name = "tbMajor")
public class Major extends Model{
    @Id
    private String id;
    private String name, level;
    private int year;
    @ManyToOne()
    private Faculty faculty;

    public Major() {
    }

    public Major(String id, String name, String level, int year, Faculty faculty) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.year = year;
        this.faculty = faculty;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public static Finder<String , Major> finder =
            new Finder<String, Major>(String.class, Major.class);

    public static List<Major> list() {
        return finder.all();
    }

    public static void create(Major major){
        major.save();
    }

    public static  void update(Major major){
        major.update();
    }
    public static void delete(Major major){
        major.delete();
    }

}

