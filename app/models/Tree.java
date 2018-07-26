package models;

import scala.util.parsing.combinator.testing.Str;

/**
 * Created by COM2-00-PC on 7/12/2018.
 */
public class Tree {
    private String id, name;
    private String detail;
    private String picture;

    public Tree() {
    }

    public Tree(String id, String name, String detail, String picture) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.picture = picture;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

