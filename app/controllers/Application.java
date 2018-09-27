package controllers;

import models.*;

import play.*;
import play.api.templates.Html;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.F;
import play.mvc.*;

import views.html.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static play.Play.application;

public class Application extends Controller {
    public static List<Tree> treeList = new ArrayList<Tree>();
    public static String picPath = "public/images/tree";
    //Student
    public static List<Student> studentList = new ArrayList<Student>();
    public static Form<Student> studentForm = Form.form(Student.class);
    public static Student student;
    //Major
    public static List<Major> majorList = new ArrayList<Major>();
    public static Form<Major> majorForm = Form.form(Major.class);
    public static Major major;
    //Faculty
    public static List<Faculty> facultyList = new ArrayList<Faculty>();


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

    public static Result testAddStudent() {
        Student bis = new Student("std002", "Kookkai", "Kaika", "BIT.", "BIS", 6);
        Student.create(bis);
        return ok();
    }

    public static Result listStudent(){
        studentList=Student.selectAll();
        return showMain(listStudent.render(studentList));
    }

    public static Result inputStudent(){
        studentForm = Form.form(Student.class);
        return showMain(inputStudent.render(studentForm));
    }

    public static Result saveStudent() {
        Form<Student> newStudent = studentForm.bindFromRequest();
        if (newStudent.hasErrors()) {
            flash("msgError","ป้อนข้อมูลไม่ถูกต้อง/ไม่สมบูรณ์ กรุณาตรวจสอบและแก้ไขให้ถูกต้อง");
            return showMain(inputStudent.render(newStudent));

        }else{
            student=newStudent.get();
            Student test = Student.finder.byId(student.getId());
            if(test!=null){
                flash("msgError","รหัสนักศึกษาที่ระบุ มีอยู่แล้วในระบบฐานข้อมูล กรุณาแก้ไขใหม่");
                return showMain(inputStudent.render(newStudent));
            }else {
                Student.create(student);
                return listStudent();
            }
        }
    }

    public static Result editStudent(String id) {
        student=Student.finder.byId(id);
        if(student!=null){
            studentForm = Form.form(Student.class).fill(student);
            return showMain(editStudent.render(studentForm));
        }else{
            return listStudent();
        }
    }

    public static Result updateStudent(){
        Form<Student> newStudent = studentForm.bindFromRequest();
        if (newStudent.hasErrors()) {
            return showMain(editStudent.render(newStudent));
        }else{
            student=newStudent.get();
            Student.update(student);
            return listStudent();
        }
    }

    public static Result deleteStudent(String id) {
        student=Student.finder.byId(id);
        if(student!=null){
            Student.delete(student);
        }
        return listStudent();
    }

    public static Result listMajor(){
        majorList=Major.list();
        return showMain(listMajor.render(majorList));
    }

    public static Result inputMajor() {
        facultyList = Faculty.list();
        majorForm = Form.form(Major.class);
        return showMain(inputMajor.render(majorForm, facultyList));
    }

    public static Result saveMajor(){
        Form<Major> newMajor = majorForm.bindFromRequest();
        if(newMajor.hasErrors()) {
            flash("msgError","ป้อนข้อมูลไม่ถูกต้อง/ไม่สมบูรณ์ กรุณาตรวจสอบและแก้ไขให้ถูกต้อง");
            facultyList = Faculty.list();
            return showMain(inputMajor.render(newMajor, facultyList));
        }else {
            major=newMajor.get();
            Major check = Major.finder.byId(major.getId());
            if(check != null){
                flash("msgError","รหัสหลักสูตรนี้ซ้ำกับที่มีอยู่แล้วในระบบ กรุณาตรวจสอบและแก้ไขให้ถูกต้อง");
                facultyList = Faculty.list();
                return showMain(inputMajor.render(newMajor, facultyList));
            }
            Major.create(major);
            return listMajor();
        }
    }

    public static Result editMajor(String id){
        facultyList = Faculty.list();
        major=Major.finder.byId(id);
        if(major==null){
            return listMajor();
        }else{
            majorForm=Form.form(Major.class).fill(major);
            return showMain(editMajor.render(majorForm, facultyList));
        }
    }

    public static Result updateMajor(){
        Form<Major> newMajor = majorForm.bindFromRequest();
        if(newMajor.hasErrors()) {
            flash("msgError","ป้อนข้อมูลไม่ถูกต้อง/ไม่สมบูรณ์ กรุณาตรวจสอบและแก้ไขให้ถูกต้อง");
            facultyList = Faculty.list();
            return showMain(editMajor.render(newMajor, facultyList));
        }else {
            major=newMajor.get();
            Major.update(major);
            return listMajor();
        }
    }
    public static Result deleteMajor(String id){
        major=Major.finder.byId(id);
        if(major!=null) {
            Major.delete(major);
        }
        return listMajor();
    }
}
