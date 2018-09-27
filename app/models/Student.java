package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by COM2-00-PC on 7/26/2018.
 */
@Entity
@Table(name="tbStudent")
public class Student  extends Model {
    @Id
    private String id;
    private String name, surname;
    private String faculty, major;
    private int level;

    public Student() {
    }

    public Student(String id, String name, String surname, String faculty, String major, int level) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
        this.major = major;
        this.level = level;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static void create(Student student){
        student.save();
    }

    public static void update(Student student){
        student.update();
    }

    public static void delete(Student student){
        student.delete();
    }

    public static Finder<String, Student> finder =
            new Finder<String, Student>(String.class, Student.class);

    public static List<Student> selectAll(){
        return finder.all();
    }


}
