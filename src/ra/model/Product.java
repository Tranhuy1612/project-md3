package ra.model;

import ra.config.InputMethods;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private Catalog catalog;
    private String description;
    private int stock; //hàng trong kho
    private boolean status = true;

    public Product() {

    }

    public Product(int id, String name, double price, Catalog catalog, String description, int stock, boolean status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.catalog = catalog;
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

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
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

    public void inputData(List<Catalog> list) {
        System.out.print("Nhập tên sản phẩm: ");
        this.name = InputMethods.getString();
        System.out.print("Nhập giá sản phẩm(lớn hơn 0): ");
        this.price = InputMethods.getDouble();
        System.out.print("Nhập vào mô tả: ");
        this.description = InputMethods.getString();
        System.out.print("Nhập vào stock: ");
        this.stock = InputMethods.getInteger();
        for (Catalog c : list) {
            System.out.println(c);
        }
        while (true) {
            boolean flag = false;
            System.out.print("Vui lòng chọn ID danh mục: ");
            int id = InputMethods.getInteger();
            for (Catalog c : list) {
                if (c.getId() == id) {
                    this.catalog = c;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            } else {
                System.err.println("Không có danh mục đó, Vui lòng chọn lại: ");
            }
        }
    }

    @Override
    public String toString() {
        String green = "\u001B[32m";
        String reset = "\u001B[0m";
        String header = green + " | Tên sản phẩm       | Giá           | Mô tả            | Kho          |  Danh mục        | Trạng thái\n";
        String separator = "------------------------------------------------------------------\n";
        String data = " ID : " + id + " | Tên : " + name + " | Giá : " + price + "đ" + " | Mô tả :" + description +
                "| Kho : " + stock + " Danh mục :" + catalog.getName() + "| Trạng thái :" + (status ? "Bán" : "không bán");
        return header + reset + separator + data;
    }

}

