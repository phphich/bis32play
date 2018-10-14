package controllers;

import models.*;

import org.jboss.netty.util.internal.ReusableIterator;
import play.*;
import play.api.templates.Html;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.F;
import play.mvc.*;

import views.html.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

        studentList=Student.selectAll();
        return showMain(listStudent.render(studentList));
    }

    public static Result inputStudent(){
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

        studentForm = Form.form(Student.class);
        return showMain(inputStudent.render(studentForm));
    }

    public static Result saveStudent() {
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }
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
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

        student=Student.finder.byId(id);
        if(student!=null){
            studentForm = Form.form(Student.class).fill(student);
            return showMain(editStudent.render(studentForm));
        }else{
            return listStudent();
        }
    }

    public static Result updateStudent(){
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

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
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

        student=Student.finder.byId(id);
        if(student!=null){
            Student.delete(student);
        }
        return listStudent();
    }

    public static Result listMajor(){
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

        majorList=Major.list();
        return showMain(listMajor.render(majorList));
    }

    public static Result inputMajor() {
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

        facultyList = Faculty.list();
        majorForm = Form.form(Major.class);
        return showMain(inputMajor.render(majorForm, facultyList));
    }

    public static Result saveMajor(){
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

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
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

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
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

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
        if(session().get("ustatus")!="Admin"){
            return showMain(home.render());
        }

        major=Major.finder.byId(id);
        if(major!=null) {
            Major.delete(major);
        }
        return listMajor();
    }


    public static Result loginForm() {
        return showMain(loginForm.render());
    }

    public static Result authen(){
        DynamicForm myForm = Form.form().bindFromRequest();
        String uid=myForm.get("uid");
        String passwd = myForm.get("passwd");

        User userLogin = User.authen(uid, passwd);
        if(userLogin == null){
            return loginForm();
        }else {
            session("uid", userLogin.getId());
            session("uname", userLogin.getName());
            session("ustatus", userLogin.getStatus());
            return showMain(home.render());

        }
    }
    public static Result logout(){
        session().clear();
        return showMain(home.render());
    }

    // Shopping
    public static  List<Animal> animalList = new ArrayList<Animal>();
    public static Animal animal;

    public static Result showAnimalSale() {
        animalList = Animal.list();
        List<Basket> basketList = (List<Basket>) Cache.get("basketList");
        return showMain(showAnimalSale.render(animalList, basketList));

    }

    public static Result addOrder(String id){
        animal = Animal.finder.byId(id);
        List<Basket> basketList=new ArrayList<Basket>();
        boolean found=false;

        if(Cache.get("basketList") != null){
            //basketList.addAll((List<Basket>) Cache.get("basketList"));
            basketList = (List<Basket>) Cache.get("basketList");

            for(int i=0;i<basketList.size();i++) {
                if(basketList.get(i).getAnimal().getId().equals(id)) {
                    int amount =  basketList.get(i).getAmount();
                    basketList.get(i).setAmount(amount + 1);
                    found=true;
                    break;
                }
            }
        }

        if(found==false) {
            basketList.add(new Basket(animal,1));
        }

        Cache.set("basketList", basketList);
        //return showAnimalSale();
        return redirect("/shopping");
    }

    public static Result removeItem(String id){
        List<Basket> basketList=new ArrayList<Basket>();

        if(Cache.get("basketList") != null){
            basketList = (List<Basket>) Cache.get("basketList");
            for(int i=0;i<basketList.size();i++) {
                if(basketList.get(i).getAnimal().getId().equals(id)) {
                    basketList.remove(i);
                    break;
                }
            }
        }

        Cache.set("basketList", basketList);
        return redirect("/shopping");
    }

    public static Result checkBill() {
        List<Basket> basketList=new ArrayList<Basket>();
        if(Cache.get("basketList") != null) {
            basketList =(List<Basket>) Cache.get("basketList");
        }
        return showMain(checkBill.render(basketList));
    }

    public static Result saveBill() {
        List<Basket> basketList=new ArrayList<Basket>();

        if(Cache.get("basketList") != null) {
            Orders orders=new Orders();
            User user = User.finder.byId(session().get("uid"));
            orders.setDate(new Date());
            orders.setUser(user);
            orders.setStatus("order");
            orders.create(orders);

            basketList=(List<Basket>) Cache.get("basketList");
            for(int i=0; i<basketList.size();i++) {
                OrdersDetail ordersDetail = new OrdersDetail();
                ordersDetail.setOrders(orders);
                ordersDetail.setAnimal(basketList.get(i).getAnimal());
                ordersDetail.setAmount(basketList.get(i).getAmount());
                OrdersDetail.create(ordersDetail);

            }
        }
        Cache.remove("basketList");
        return redirect("/shopping");
    }

}
