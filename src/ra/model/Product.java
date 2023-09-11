package ra.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private Catalog productCatalog;
    private String description;
    private int stock; //hàng trong kho
    private boolean status = true;

    public Product() {

    }

    public Product(Catalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public Catalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(Catalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public Product(int id, String name, double price, String description, int stock, boolean status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.status = status;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String yellow = "\u001B[33m";
        System.out.println(yellow + " | Name               | Price                       | Description                | Stock             | Status");
        System.out.println("------------------------------------------------------------------");
        return " ID : " + id + " | Name : " + name + " | Price : " + price + "đ" + " | Description :" + description + "| Stock : " + stock + "| status :" + (status ? "sell" : "not sold");
    }
}
