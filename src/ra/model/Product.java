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
        System.out.print("Nhập giá sản phẩm: ");
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
        return String.format("%-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-10s ",
                "Mã sản phẩm", "Tên sản phẩm", "Giá", "Mô tả", "Trong kho", "Danh mục", " Trạng thái")
                + "\n" + "-".repeat(100)
                + String.format("\n%-10s  | %-20s | %-10s | %-10s | %-10s | %-10s | %-10s",
                id, name, price + " $ ", description, stock, catalog.getName(), (status ? " Đang bán " : " Hết hàng "))
                + "\n" + "-".repeat(100);

    }
}

