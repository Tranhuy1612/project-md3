package ra.view;

import ra.config.InputMethods;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.model.RoleName;
import ra.model.User;
import ra.service.UserService;


public class Navbar {
    private static UserController userController = new UserController();
    private static ProductController productController = new ProductController();
    private OrderController orderController;


    public static User userLogin;
    // Tất cả giao diện điều hướng

    public static void menuStore() {
        while (true) {
            String green = "\u001B[32m";
            System.out.println(green + "╔═════════════════════════════════════╗");
            System.out.println(green + "║           Menu-Store                ║");
            System.out.println(green + "╟────────┬────────────────────────────╢");
            System.out.println(green + "║Lựa chọn│       Miêu tả              ║");
            System.out.println(green + "╟────────┼────────────────────────────╢");
            System.out.println(green + "║   1    │       Đăng nhập            ║");
            System.out.println(green + "║   2    │       Đăng kí              ║");
            System.out.println(green + "║   3    │       Hiển thị sản phẩm    ║");
            System.out.println(green + "║   4    │       Thoát                ║");
            System.out.println(green + "╚════════╧════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    //  đăng nhập
                    login();
                    break;
                case 2:
                    // đăng kí
                    register();
                    break;
                case 3:
                    // hiển thị sản phẩm
                    ProductManager.displayListProduct();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.err.println("vui lòng nhập số từ 1 đến 4");
            }

        }
    }

    public static void menuUser() {
        while (true) {
            System.out.println("\u001B[32m╔════════════════════════════════════════╗");
            System.out.println("\u001B[32m║           Menu-User                    ║");
            System.out.println("\u001B[32m╟────────┬───────────────────────────────╢");
            System.out.println("\u001B[32m║Lựa chọn│         Miêu tả               ║");
            System.out.println("\u001B[32m╟────────┼───────────────────────────────╢");
            System.out.println("\u001B[32m║   1    │ Hiển thị sản phẩm             ║");
            System.out.println("\u001B[32m║   2    │ Tìm sản phẩm theo tên         ║");
            System.out.println("\u001B[32m║   3    │ Thêm vào giỏ hàng             ║");
            System.out.println("\u001B[32m║   4    │ Xem giỏ hàng                  ║");
            System.out.println("\u001B[32m║   5    │ lịch sử đơn hàng              ║");
            System.out.println("\u001B[32m║   6    │ Thay đổi hồ sơ                ║");
            System.out.println("\u001B[32m║   7    │ Đổi mật khẩu                  ║");
            System.out.println("\u001B[32m║   8    │ Danh sách yêu thích           ║");
            System.out.println("\u001B[32m║   9    │ Payment (Ví)                  ║");
            System.out.println("\u001B[32m║   0    │ Thoát                         ║");
            System.out.println("\u001B[32m╚═════──═╧═══════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:                              ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // hiển thị danh sách sản phẩm
                    ProductManager.displayListProduct();
                    break;
                case 2:
                    // tìm kiếm sản phẩm theo tên
                    ProductManager.searchByName();
                    break;
                case 3:
                    // mua hàng
                    CartManager.addToCart();
                    break;
                case 4:
                    // quản lí giỏ hàng
                    new CartManager();
                    break;
                case 5:
                    // lịch sử mua hàng
                    new OrderManager();
                    break;
                case 6:
                    // Thay đổi thông tin cá nhân
                    profileChange(userLogin.getId());
                    break;
                case 7:
                    //thay đổi mật khẩu
                    UserMananger.changePassword();
                    break;
                case 8:
                    // quản lí sở thích
                    FavoritesManager.FavoritesList();
                    break;
                case 9:
                    payment();
                    break;
                case 0:
                    logOut();
                    break;
                default:
                    System.err.println("vui lòng nhập số từ 1 đến 9");
            }
            if (choice == 0) {
                break;
            }
        }
    }

    public static void payment() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║              Menu-Admin              ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[32m║   1    │ Hiển thị số dư tài khoản    ║");
            System.out.println("\u001B[32m║   2    │ Thêm tiền vào ví của bạn    ║");
            System.out.println("\u001B[32m║   0    │ Trở về                      ║");
            System.out.println("\u001B[32m╚════════╧═════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:                            ");

            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showWallet();
                    break;
                case 2:
                    putMoney();
                    break;
                case 0:
                    Navbar.menuUser();
                    break;
                default:
                    System.out.println("vui lòng nhập số từ 1 đến 2");
            }
        }
    }

    public static void showWallet() {
        User walletUser = Navbar.userLogin;
        double wallet = walletUser.getWallet();
        System.out.println("Số dư tài khoản của bạn là: " + wallet);
    }

    public static void putMoney() {
        System.out.println("Nhập số tiền : ");
        double putMoney = InputMethods.getInteger();
        System.out.println("Đã gửi tiền \uD83D\uDCB0  Thành công ");
        double olwMoney = Navbar.userLogin.getWallet();
        Navbar.userLogin.setWallet(olwMoney + putMoney);
    }

    public static void profileChange(int id) {
        User userLogin = userController.findById(id);
        System.out.println("+----------------------------------+");
        System.out.println("|         Thông tin cá nhân        |");
        System.out.println("+----------------------------------+");
        System.out.println("| Tên        | " + userLogin.getName() + "             |");
        System.out.println("| Email      | " + userLogin.getEmail() + "   |");
        System.out.println("| Phone      | " + userLogin.getPhoneNumber() + "          |");
        System.out.println("+----------------------------------+");
        System.out.println("|Bạn có muốn thay đổi thông tin không ? |");
        System.out.println("|thông tin?                        |");
        System.out.println("|yes để đồng ý thay đổi            |");
        System.out.println("| --- No để thoát                  |"); // no để thoát
        System.out.println("+----------------------------------+");

        String check = InputMethods.getString();
        if (check.trim().toLowerCase().equals("yes")) {
            User userEdit = new User();
            userEdit.setId(userLogin.getId());
            userEdit.setStatus(userLogin.isStatus());
            userEdit.setId(userLogin.getId());
            userEdit.setStatus(userLogin.isStatus());
            userEdit.setCart(userLogin.getCart());
            userEdit.setFavorites(userLogin.getFavorites());
            userEdit.setRoles(userLogin.getRoles());
            userEdit.setUsername(userLogin.getUsername());
            System.out.println("Nhập tên :");
            userEdit.setName(InputMethods.getString());
            System.out.println("Nhập Email");
            userEdit.setEmail(InputMethods.getEmail());
            userEdit.setPassword(userLogin.getPassword());
            System.out.println("Nhập số điện thoai : ");
            userEdit.setPhoneNumber((InputMethods.getPhoneNumber()));
            System.out.println("Nhập yes để xác nhận thay đổi -----No hủy bỏ");
            String confirmChoice = InputMethods.getString();
            if (confirmChoice.trim().toLowerCase().equals("yes")) {
                userController.save(userEdit);
                System.out.println("Thay đổi thành công");
            } else {
                System.out.println("Hủy bỏ thay đổi");
            }

        }
    }

    public static void menuAdmin() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║              Menu-Admin              ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[32m║   1    │      Quản lí tài khoản      ║");
            System.out.println("\u001B[32m║   2    │      Quản lí danh mục       ║");
            System.out.println("\u001B[32m║   3    │      Quản lí sản phẩm       ║");
            System.out.println("\u001B[32m║   4    │      Quản lý đơn hàng       ║");
            System.out.println("\u001B[32m║   0    │      Trở về                 ║");
            System.out.println("\u001B[32m╚════════╧═════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:                           ");

            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // Quản lí tài khoản người dùng
                    new UserMananger(userController);
                    break;
                case 2:
                    // quản lí danh mục / mục lục
                    new CatalogManager();
                    break;
                case 3:
                    // quản lí sản phẩm
                    new ProductManager();
                    break;
                case 4:
                    // Trang chủ quản lí đặt hàng
                    new OrderManager(new OrderController());
                    break;
                case 0:
                    logOut();
                    break;
                default:
                    System.err.println("vui lòng nhập số từ 1 đến 4 ");
            }
            if (choice == 0) {
                break;
            }
        }
    }

    public static void menuAccountManager() {
        System.out.println("\u001B[32m╔══════════════════════════════════════╗");
        System.out.println("\u001B[32m║          Menu-Account-Manager        ║");
        System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
        System.out.println("\u001B[32m║   1    │   Hiển thị tất cả tài khoản ║");
        System.out.println("\u001B[32m║   2    │   Chặn/Bỏ chặn tài khoản    ║");
        System.out.println("\u001B[32m║   3    │   Trở về                    ║");
        System.out.println("\u001B[32m╚════════╧═════════════════════════════╝");
        System.out.println("Nhập lựa chọn của bạn:                           ");


    }

    public static void menuCart() {
        System.out.println("\u001B[32m╔══════════════════════════════════════╗");
        System.out.println("\u001B[32m║              Menu-Cart               ║");
        System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
        System.out.println("\u001B[32m║   1    │    Hiển thị giỏ hàng        ║");
        System.out.println("\u001B[32m║   2    │    Thay đổi số lượng        ║");
        System.out.println("\u001B[32m║   3    │    Xóa Item                 ║");
        System.out.println("\u001B[32m║   4    │    Xóa tất cả               ║");
        System.out.println("\u001B[32m║   5    │    Thanh toán               ║");
        System.out.println("\u001B[32m║   6    │    Trở về                   ║");
        System.out.println("\u001B[32m╚════════╧═════════════════════════════╝");
        System.out.println("Nhập lựa chọn của bạn     :                      ");

    }

    public static void login() {
        System.out.println("-------------Đăng nhập-------------");
        System.out.println("Nhập username");
        String username = InputMethods.getUseName();
        System.out.println("Nhập password");
        String password = InputMethods.getPassword();
        // kiem tra dang nhap
        User user = userController.login(username, password);
        if (user == null) {
            System.err.println("Đăng nhập thất bại");
        } else {
            if (user.getRoles().contains(RoleName.ADMIN)) {
                userLogin = user;
                menuAdmin();
            } else {
                if (user.isStatus()) {
                    userLogin = user;
                    menuUser();
                } else {
                    System.err.println("Tài khoản của bạn đã bị khóa");
                    login();
                }
            }
        }

    }

    public static void register() {
        System.out.println("-------------Đăng kí-------------");
        User user = new User();
        user.setId(userController.getNewId());
        System.out.println("ID : " + user.getId());
        System.out.println("Nhập tên :");
        user.setName(InputMethods.getUseName());
        System.out.println("Nhập Email :");
        user.setEmail(InputMethods.getEmail());
        System.out.println("Nhập số điện thoại :");
        user.setPhoneNumber(InputMethods.getPhoneNumber());
        System.out.println("Nhập Username");
        user.setUsername(InputMethods.getUseName());
        System.out.println("Nhập Password");
        user.setPassword(InputMethods.getPassword());

        for (User u : userController.findAll()) {
            String existingPhoneNumber = u.getPhoneNumber();
            if (existingPhoneNumber != null &&
                    (existingPhoneNumber.equals(user.getPhoneNumber()) ||
                            u.getUsername().equals(user.getUsername()) ||
                            u.getEmail().equals(user.getEmail()))) {
                System.err.println("Tài khoản đã tồn tại");
                return;
            }
        }
        userController.save(user);
        System.out.println("\u001B[34m" + "Đăng ký thành công" + "\u001B[0m");
        System.out.println("\u001B[34m" + "Vui lòng hãy đăng nhập" + "\u001B[0m");
        login();

    }

    public static void logOut() {
        userLogin = null;
        menuStore();
        // con tro ddung
    }


}
