package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.model.Product;

import java.util.List;

import static ra.view.Navbar.userLogin;

public class FavoritesManager {
    private UserController userController;

    public static void FavoritesList() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║             Menu-favorites          \u001B[0m ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│      show favorites list   \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│      add list favorites    \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│      delete favorites      \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m0    \u001B[34m│      Back                  \u001B[0m ║");
            System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
            System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    //  hiển thị danh sách yêu thích
                    displayFavorites();
                    break;
                case 2:
                    // thêm danh sách yêu thích
                    addToFavorites();
                    break;
                case 3:
                    // xóa danh sách yêu thích
                    deleteFavorites();
                    break;
                case 0:
                    Navbar.menuUser();
                    break;
                default:
                    System.err.println("please enter number from 1 to 3");
            }

        }
    }

    public static void addToFavorites() {
        List<Integer> listFavorites = userLogin.getFavorites();
        System.out.println("Please enter the product id"); // nhập id sản phẩm
        int id = InputMethods.getInteger();
        ProductController productController = new ProductController();
        if (productController.findById(id) == null) {
            System.err.println(Constants.NOT_FOUND); // không tìm thấy sản phâm
            return;
        }
        for (Integer idfavorite : listFavorites
        ) {
            if (idfavorite == id) {
                System.out.println("already in favorites"); // đã có trong danh sách yêu thích
                return;
            }
        }
        listFavorites.add(id);
        System.out.println("add to favorites success"); // thêm vào yêu thích thành công
    }


    public static void displayFavorites() {
        List<Integer> listFavorites = userLogin.getFavorites();
        ProductController productController = new ProductController();
        if (listFavorites.isEmpty()) {
            System.err.println("List is empty "); // danh sách rỗng
            return;
        }
        for (Integer id : listFavorites
        ) {
            Product p = productController.findById(id);
            System.out.println(p);
        }
    }

    public static void deleteFavorites() {
        List<Integer> listFavorites = userLogin.getFavorites();
        if (listFavorites.isEmpty()) {
            System.err.println("Favorite list is empty."); // Danh sách yêu thích rỗng
            return;
        }

        System.out.println("Enter the product ID to delete:"); // Nhập ID sản phẩm cần xóa
        int id = InputMethods.getInteger();
        boolean found = false;

        for (int i = 0; i < listFavorites.size(); i++) {
            if (id == listFavorites.get(i)) {
                listFavorites.remove(i);
                found = true;
                System.out.println("Successfully deleted."); // Xóa thành công
                break;
            }
        }
        if (!found) {
            System.err.println("Product ID not found in favorites."); // Không tìm thấy ID sản phẩm trong danh sách yêu thích
        }
    }

}
