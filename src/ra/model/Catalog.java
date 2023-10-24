package ra.model;

import ra.config.InputMethods;

import java.io.Serializable;

public class Catalog implements Serializable {
    private int id;
    private String name;

    public Catalog() {
    }

    public Catalog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public  void inputData(){
        System.out.println("nhập tên danh mục :");
        this.name= InputMethods.getString();
    }
    @Override
    public String toString() {
        return "Id : " + id + "| Tên :" + name ;
    }
}
