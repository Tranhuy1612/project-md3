package ra.controller;

import ra.model.Product;
import ra.service.ProductService;

import java.util.List;

public class ProductController {
    private ProductService productService;

    public ProductController() {
        productService = new ProductService();
    }

    public List<Product> findAll() {
        return productService.findAll();
    }

    ;

    public Product findById(int id) {
        return productService.findById(id);
    }

    public void save(Product p) {
        productService.save(p);
    }

    public void delete(int id) {
        productService.delete(id);
    }

    public List<Product> searchByName(String searchName) {
        return productService.searchByName(searchName);
    }

}
