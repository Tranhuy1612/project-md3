package ra.view;

import ra.config.InputMethods;
import ra.controller.CatalogController;
import ra.controller.ProductController;
import ra.model.Catalog;
import ra.model.Product;

import java.util.List;

public class CatalogManager {
    private static ProductController productController = new ProductController();
    private static List<Product> products = productController.findAll();
    private static CatalogController catalogController = new CatalogController();
    private static List<Catalog> catalogs = catalogController.findAll();

    public CatalogManager() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║             Menu-Catalog             ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[32m║   1    │      Hiển thị danh mục      ║");
            System.out.println("\u001B[32m║   2    │      Tạo danh mục           ║");
            System.out.println("\u001B[32m║   3    │      Sửa danh mục           ║");
            System.out.println("\u001B[32m║   4    │      Xóa danh mục           ║");
            System.out.println("\u001B[32m║   5    │      Tìm kiếm theo tên      ║");
            System.out.println("\u001B[32m║   0    │      Trở về                 ║");
            System.out.println("\u001B[32m╚════════╧═════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:                            ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // xem danh mục
                    showCatalog();
                    break;
                case 2:
                    //tạo danh mục
                    createCatalog();
                    break;
                case 3:
                    //update danh mục
                    updateCatalog();
                    break;
                case 4:
                    //xóa danh mục
                    deleteCatalog();
                    break;
                case 5:
                    //tìm kiếm theo tên danh mục
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

    public void showCatalog() {
        if (catalogController.findAll().size() == 0) {
            System.err.println("Chưa có danh mục nào ");
            return;
        }
        for (Catalog c : catalogs) {
            System.out.println(c);
        }
    }

    public void createCatalog() {
        System.out.print("Bạn muốn thêm vào số lương bao nhiêu : ");
        int n = InputMethods.getInteger();
        for (int i = 0; i < n; i++) {
            System.out.println("Danh mục thứ " + (i + 1));
            Catalog catalog = new Catalog();
            catalog.setId(catalogController.getNewId());
            catalog.inputData();
            for (Catalog c : catalogController.findAll()) {
                if (c.getName().toLowerCase().equals(catalog.getName().toLowerCase())) {
                    System.err.println("Tên thương  đã tồn tại");
                    return;
                }
            }
            System.out.println("Bạn đã thêm thành công \uD83D\uDC4C");
            catalogController.save(catalog);
        }
    }

    public void updateCatalog() {
        if (catalogController.findAll().isEmpty()) {
            System.err.println("chưa có danh mục nào ");
            return;
        }
        System.out.print("Nhập tên danh mục cần sửa : ");
        int id = InputMethods.getInteger();
        Catalog catalog = catalogController.findById(id);
        if (catalog == null) {
            System.err.println("Không tồn tại danh mục này");
            return;
        }
        Catalog newCatalog = new Catalog();
        newCatalog.setId(catalog.getId());
        newCatalog.inputData();
        System.out.println("Bạn đã sửa thành công \uD83D\uDC4C ");
        catalogController.save(newCatalog);
    }

    public void deleteCatalog() {
        ProductController productController = new ProductController();
        List<Product> productList = productController.findAll();
        if (catalogController.findAll().isEmpty()) {
            System.err.println("chưa có danh mục nào ");
            return;
        }
        System.out.print(" Nhập mã danh mục cần xoá ");
        int id = InputMethods.getInteger();
        if (!productList.isEmpty()) {
            for (Product p : productList
            ) {
                if (p.getCatalog().getId() == id) {
                    System.err.println("không thể xoá vì vẫn còn sản phẩm của danh mục này này");
                    return;
                }
            }
        }
        if (catalogController.findById(id) == null) {
            System.err.println("không tìm thấy danh mục cần xoá");

        } else {
            System.out.println(" Đã xoá thành công ✅");
            catalogController.delete(id);
        }
    }

    public void searchByName() {
        System.out.println("Nhập tên cần tìm :");
        String searchName = InputMethods.getString();

        List<Catalog> foundCatalogs = catalogController.searchByName(searchName);
        if (foundCatalogs.isEmpty()) {
            System.err.println("không tìm thấy danh mục nào có tên được chỉ định ");
        } else {
            for (Catalog c : foundCatalogs) {
                System.out.println(c);
            }
        }
    }

}
