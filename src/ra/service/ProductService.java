package ra.service;

import ra.model.Product;
import ra.util.DataBase;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IGenericService<Product, Integer> {
    //Tạo đối tượng User, đây là nơi lưu trữ dữ liệu người dùng trong bộ nhớ.
    private List<Product> products;


    // Một đối tượng của lớp DataBase, được sử dụng để đọc và ghi dữ liệu từ file.
    private DataBase<Product> productData = new DataBase();

    public ProductService() {
        List<Product> list = productData.readFromFile(DataBase.PRODUCT_PATH);
        if (list == null) {
            list = new ArrayList<>();
        }
        this.products = list;// du lieu doc tu file
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getId()) == null) {
            products.add(product);
        } else {
            products.set(products.indexOf(findById(product.getId())), product);
        }
        productData.writeToFile(products, DataBase.PRODUCT_PATH);
    }

    @Override
    public void delete(Integer id) {
        Product product = findById(id);
        if (product != null) {
            products.remove(product);
            productData.writeToFile(products, DataBase.PRODUCT_PATH);
        }
    }

    @Override
    public Product findById(Integer id) {

        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Product> searchByName(String searchName) {
        List<Product> productListSearch = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(searchName.toLowerCase())) {
                productListSearch.add(p);
            }
        }
        return productListSearch;
    }
    public int getNewId() {
        int max = 0;
        for (Product p : products
        ) {
            if (p.getId() > max) {
                max = p.getId();
            }
        }
        return max + 1;
    }
}
