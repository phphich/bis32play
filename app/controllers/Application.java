package controllers;

import models.Animal;
import models.Tree;
import play.*;
import play.api.templates.Html;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static play.Play.application;

public class Application extends Controller {
    public static List<Tree> treeList = new ArrayList<Tree>();
    public static String picPath = "public/images/tree";

    public static Result showMain(Html content) {
        return  ok(main.render(content));
    }

    public static Result index() {
        return showMain(home.render());
    }

    public static Result page1() {
        return showMain(page1.render());
    }

    public static Result page2() {
        return showMain(page2.render());
    }
    public static Result page3() {
        return showMain(page3.render());
    }

    public static Result showAnimal(){
        Animal mydog = new Animal();
        mydog.setId("d001");
        mydog.setName("Rambo");
        mydog.setPrice(55000.00);
        mydog.setAmount(10);

        Animal mycat= new Animal("c001","Kadekanok", 200.00, 5);
        return showMain(showAnimal.render(mydog, mycat));
    }

    public static Result getTree() {
        return showMain(getTree.render());
    }

    public static Result addTree(){
        Http.MultipartFormData body =  request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("picture");

        DynamicForm myForm = Form.form().bindFromRequest();
        String id, name, detail;
        id = myForm.get("id");
        name= myForm.get("name");
        detail=myForm.get("detail");
        Tree tree = new Tree();
        tree.setId(id);
        tree.setName(name);
        tree.setDetail(detail);

        String fileName="";
        String contentType;

        if (picture != null) {
            contentType = picture.getContentType();
            File file = picture.getFile();

            fileName= picture.getFilename();
            if (!contentType.startsWith("image")) {
                return ok(showError.render());
            }
            else {
                file.renameTo(new File(picPath, fileName));
                tree.setPicture(fileName);
                treeList.add(tree);
                return showTree();
            }
        } else {
            return ok(showError.render());
        }
    }

    public static Result showTree() {
        return showMain(showTree.render(treeList));
    }

    public static Result inputAnimal() {
        return showMain(inputAnimal.render());
    }
    public static Result postAminal() {
        DynamicForm myform = Form.form().bindFromRequest();
        String id, name;
        double price;
        int amount;

        if(myform.hasErrors()){
            //return showMain(inputAnimal.render());
            return inputAnimal();
        }else{
            id = myform.get("id");
            name= myform.get("name");
            price=Double.parseDouble(myform.get("price"));
            amount = Integer.parseInt(myform.get("amount"));
            Animal myanimal = new Animal();
            myanimal.setId(id);
            myanimal.setName(name);
            myanimal.setPrice(price);
            myanimal.setAmount(amount);
            return showMain(postAnimal.render(myanimal));
        }
    }
}
