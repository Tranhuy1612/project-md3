package ra.service;

import ra.model.Product;
import ra.util.DataBase;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IGenericService<Product, Integer> {
    private List<Product> products;
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
        for (Product p : products) {
            if (p.getId() == id) {
                products.remove(p);
                productData.writeToFile(products, DataBase.PRODUCT_PATH);
                return;
            }
        }
        System.err.println("Id not found !");
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

}
