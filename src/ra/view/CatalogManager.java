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
            System.out.println("\u001B[32m║             Menu-Catalog            \u001B[0m ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│      Show Catalog          \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│      Add Catalog           \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│      Update Catalog        \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m4    \u001B[34m│      Delete Catalog        \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m5    \u001B[34m│      Search by Name        \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m0    \u001B[34m│      Back                  \u001B[0m ║");
            System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
            System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");
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
                    System.err.println("please enter number from 1 to 5");
            }

        }
    }

    public void showCatalog() {
        for (Catalog c : catalogs) {
            System.out.println(c);
        }
    }

    public void createCatalog() {
        Catalog catalog = new Catalog();
        if (catalogs.size() == 0) {
            catalog.setId(1);
        } else {
            catalog.setId(catalogs.get(catalogs.size() - 1).getId() + 1);
        }
        System.out.println("Enter the name :");
        catalog.setName(InputMethods.getString());
        System.out.println("Enter the status :");
        catalog.setStatus(InputMethods.getBoolean());
        catalogController.save(catalog);
    }

    public void updateCatalog() {
        System.out.println("Enter the id to update :");
        int id = InputMethods.getInteger();
        if (catalogController.findById(id) == null) {
            System.out.println("id not found !");
        } else {
            System.out.println("Enter name change to :");
            String nameUp = InputMethods.getString();
            System.out.println("Enter status change to :");
            boolean status = InputMethods.getBoolean();
            Catalog catalog = new Catalog(id, nameUp, status);
            catalogController.save(catalog);
        }
    }

    public void deleteCatalog() {
        System.out.println("Enter ID to delete:");
        int id = InputMethods.getInteger();

        Catalog catalogToDelete = catalogController.findById(id);
        if (catalogToDelete == null) {
            System.err.println("ID not found!");
        } else {
            catalogController.delete(id);
            System.out.println("Catalog deleted successfully."); // xóa catalog thành công
        }
    }
    public void searchByName() {
        System.out.println("Enter the search name:");
        String searchName = InputMethods.getString();

        List<Catalog> foundCatalogs = catalogController.searchByName(searchName);
        if (foundCatalogs.isEmpty()) {
            System.err.println("No catalogs found with the specified name."); // không tìm thấy danh mục nào có tên được chỉ định
        } else {
            for (Catalog c : foundCatalogs) {
                System.out.println(c);
            }
        }
    }

}
//    public void searchCatalog() {
//        System.out.println("Enter the search catalog");
//        String seacrchName = InputMethods.getString();
//        if (products.isEmpty()) {
//            System.err.println("empty");
//        }
//        for (Product p : products) {
//            if (p.getProductCatalog().getName().equals(seacrchName))
//                System.out.println(p);
//        }
//    }
