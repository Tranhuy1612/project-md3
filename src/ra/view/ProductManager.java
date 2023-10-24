package ra.view;

import ra.config.InputMethods;
import ra.controller.CatalogController;
import ra.controller.ProductController;
import ra.model.Product;

import java.util.List;

public class ProductManager {
    private static CatalogController catalogController = new CatalogController();
    private static ProductController productController = new ProductController();
    List<Product> products = productController.findAll();

    public ProductManager() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║          Product-Manager             ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[32m║   1    │     Hiển thị sản phẩm       ║");
            System.out.println("\u001B[32m║   2    │     Tạo sản phẩm            ║");
            System.out.println("\u001B[32m║   3    │     Sửa sản phẩm            ║");
            System.out.println("\u001B[32m║   4    │     Xóa sản phẩm            ║");
            System.out.println("\u001B[32m║   5    │     Tìm kiếm theo tên       ║");
            System.out.println("\u001B[32m║   0    │     Trở về                  ║");
            System.out.println("\u001B[32m╚════════╧═════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:                            ");
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
                    System.err.println("vui lòng nhập số từ 1 đến 5");
            }

        }
    }

    public static void displayListProduct() {
        ProductController productController = new ProductController();
        if (productController.findAll().isEmpty()) {
            System.err.println("Chưa có sản phẩm nào");
            return;
        }
        for (Product p : productController.findAll()) {
            if (p.isStatus()) {
                System.out.println(p);
            }
        }
    }

    public void createProduct() {
        System.out.print("Bạn muốn thêm vào bao nhiêu sản phẩm : ");
        int n = InputMethods.getInteger();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i + 1));
            Product product = new Product();
            product.setId(productController.getNewId());
            product.inputData(catalogController.findAll());
            for (Product p : productController.findAll()) {
                if (p.getName().toLowerCase().equals(product.getName().toLowerCase())) {
                    System.err.println("Tên sản phẩm đã tồn tại");
                    return;
                }
            }
            System.out.println("Thêm sản phẩm thành công ✅");
            productController.save(product);
        }
    }

    public void deleteProduct() {
        if (productController.findAll().isEmpty()){
            System.err.println("chưa có sản phẩm nào");
            return;
        }
        System.out.print("Nhập vào Mã sản phẩm: ");
        int id = InputMethods.getInteger();
        if (productController.findById( id ) == null){
            System.err.println("không tìm thấy sản phẩm cần xoá");
            return;
        }
        System.out.println(" Đã xoá thành công ✅");
        productController.delete(id);
    }

    public void updateProduct() {
        if (productController.findAll().isEmpty()) {
            System.err.println("chưa có sản phẩm  nào");
            return;
        }
        System.out.print("Nhập vào mã sản phẩm cần sửa: ");
        int id = InputMethods.getInteger();
        Product product = productController.findById(id);
        if (product == null) {
            System.err.println("Không có sản phẩm bạn muốn tìm");
            return;
        }
        Product newProduct = new Product();
        newProduct.setId(product.getId());
        newProduct.inputData(catalogController.findAll());
        System.out.println(" Bạn đã sửa thành công ✅");
        productController.save(newProduct);
    }

    public static void searchByName() {
        System.out.println("Nhập vào tên cần tìm :");
        String searchName = InputMethods.getString();
        List<Product> searchResults = productController.searchByName(searchName);
        if (searchResults.isEmpty()) {
            System.err.println("không tim thấy sản phẩm ");
        } else {
            for (Product p : searchResults) {
                System.out.println(p);
            }
        }
    }
}
