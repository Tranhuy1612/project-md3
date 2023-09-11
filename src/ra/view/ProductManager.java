package ra.view;

import ra.config.InputMethods;
import ra.controller.CatalogController;
import ra.controller.ProductController;
import ra.model.Catalog;
import ra.model.Product;

import java.util.List;

public class ProductManager {
    private static ProductController productController = new ProductController();
    List<Product> products = productController.findAll();

    public ProductManager() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║          Product-Manager            \u001B[0m ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│       Show Product         \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│       Create Product       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│       Update Product       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m4    \u001B[34m│       Delete Product       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m5    \u001B[34m│       Search by Name       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m0    \u001B[34m│       Back                 \u001B[0m ║");
            System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
            System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // xem sản phẩm
                    displayListProduct();
                    break;
                case 2:
                    //tạo sản phẩm
                    createProduct();
                    break;
                case 3:
                    //update sản phẩm
                    updateProduct();
                    break;
                case 4:
                    //xóa sản phẩm
                    deleteProduct();
                    break;
                case 5:
                    //tìm kiếm sản phẩm theo tên
                    searchByName();
                    break;
                case 0:
                    Navbar.menuAdmin();
                    break;
                default:
                    System.err.println("please enter number from 1 to 5");
            }

        }
    }

    public static void displayListProduct() {
        ProductController productController = new ProductController();
        for (Product p : productController.findAll()) {
            if (p.isStatus()) {
                System.out.println(p);
            }
        }
    }

    public void createProduct() {
        Product product = new Product();
        if (products.size() == 0) {
            product.setId(1);
        } else {
            product.setId(products.get(products.size() - 1).getId() + 1);
        }
        System.out.println("Enter the name:");
        product.setName(InputMethods.getString());
        System.out.println("Enter the price:");
        product.setPrice(InputMethods.getDouble());
        System.out.println("Enter the Description :");
        product.setDescription(InputMethods.getString());
        System.out.println("Enter the Stock");
        product.setStock(InputMethods.getInteger());
        System.out.println("Enter the status:");
        product.setStatus(InputMethods.getBoolean());
        productController.save(product);
    }

    public void deleteProduct() {
        System.out.println("Enter the id to delete: ");
        int id = InputMethods.getInteger();
        Product product = productController.findById(id);
        if (product != null) {
            productController.delete(id);
            System.out.println("\u001B[32mProduct with ID " + id + " has been deleted successfully.\u001B[0m");
        } else {
            System.err.println("Product with ID " + id + " was not found. Deletion failed.");
        }
    }

    public void updateProduct() {
        System.out.println("Enter the id to update:");
        int idUpdate = InputMethods.getInteger();
        if (productController.findById(idUpdate) == null) {
            System.err.println(" Id not found !");
        } else {
            System.out.println("Enter the name to change:");
            String nameUpdate = InputMethods.getString();
//            Catalog catalogUpdate = null;
//            List<Catalog> catalogs = new CatalogController().findAll();
//            for (Catalog c : catalogs) {
//                System.out.println(c);
//            }
//            System.out.println("Enter the catalog to change by id :");
//            int idC = InputMethods.getInteger();
//            for (Catalog c : catalogs) {
//                if (c.getId() == idC) {
//                    catalogUpdate = c;
//                }
//            }
            System.out.println("Enter the price to change");
            Float priceUpdate = InputMethods.getFloat();
            System.out.println("Enter the description");
            String descriptionUpdate = InputMethods.getString();
            System.out.println("Enter the Stock");
            int StockUpdate = InputMethods.getInteger();
            System.out.println("Enter the status to change:");// trạng thái cần thay đổi
            Boolean statusUpdate = InputMethods.getBoolean();
            Product productUpdate = new Product(idUpdate, nameUpdate, priceUpdate, descriptionUpdate, StockUpdate, statusUpdate);
            productController.save(productUpdate);
        }
    }

    public static void searchByName() {
        System.out.println("Enter the search Name:");
        String searchName = InputMethods.getString();
        List<Product> searchResults = productController.searchByName(searchName);
        if (searchResults.isEmpty()) {
            System.err.println("product not found ");// không tim thấy sản phẩm
        } else {
            for (Product p : searchResults) {
                System.out.println(p);
            }
        }
    }
}
