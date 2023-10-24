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
            String green = "\u001B[32m";
            System.out.println(green + "╔═════════════════════════════════════╗");
            System.out.println(green + "║              Menu-favorites         ║");
            System.out.println(green + "╟───────┬─────────────────────────────╢");
            System.out.println(green + "║   1   │Hiên thị danh sách yêu thích ║");
            System.out.println(green + "║   2   │Thêm vào danh sách yêu thích ║");
            System.out.println(green + "║   3   │Xóa khỏi danh sách yêu thích ║");
            System.out.println(green + "║   0   │Trở về                       ║");
            System.out.println(green + "╚═══════╧═════════════════════════════╝");
            System.out.println(green + "Nhập lựa chọn của bạn:                 ");
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
        System.out.println("Nhập id sản phẩm");
        int id = InputMethods.getInteger();
        ProductController productController = new ProductController();
        if (productController.findById(id) == null) {
            System.err.println(Constants.NOT_FOUND); // không tìm thấy sản phâm
            return;
        }
        for (Integer idfavorite : listFavorites
        ) {
            if (idfavorite == id) {
                System.out.println("Đã có trong danh sách yêu thích");
                return;
            }
        }
        listFavorites.add(id);
        System.out.println("Thêm vào yêu thích thành công");
    }


    public static void displayFavorites() {
        List<Integer> listFavorites = userLogin.getFavorites();
        ProductController productController = new ProductController();
        if (listFavorites.isEmpty()) {
            System.err.println("Danh sách rỗng");
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
            System.err.println("Danh sách yêu thích rỗng");
            return;
        }

        System.out.println("Nhập ID sản phẩm cần xóa");
        int id = InputMethods.getInteger();
        boolean found = false;

        for (int i = 0; i < listFavorites.size(); i++) {
            if (id == listFavorites.get(i)) {
                listFavorites.remove(i);
                found = true;
                System.out.println("Xóa thành công");
                break;
            }
        }
        if (!found) {
            System.err.println("Không tìm thấy ID sản phẩm trong danh sách yêu thích");
        }
    }

}
