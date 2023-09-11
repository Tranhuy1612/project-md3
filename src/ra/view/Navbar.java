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
            System.out.println("\u001B[36m╔══════════════════════════════════════╗");
            System.out.println("║           \u001B[1mMenu-Store\u001B[0m                 ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║ \u001B[32mOption \u001B[32m│         \u001B[32mDescription         ║");
            System.out.println("╟────────┼─────────────────────────────╢");
            System.out.println("║   \u001B[1m1\u001B[0m    │           \u001B[1mLogin\u001B[0m             ║");
            System.out.println("║   \u001B[1m2\u001B[0m    │          \u001B[1mRegister\u001B[0m           ║");
            System.out.println("║   \u001B[1m3\u001B[0m    │        \u001B[1mView product\u001B[0m         ║");
            System.out.println("║   \u001B[1m4\u001B[0m    │            \u001B[1mExit\u001B[0m             ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Enter your choice:");
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
                    System.err.println("please enter number from 1 to 4");
            }

        }
    }

    public static void menuUser() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║             \u001B[1mMenu-User\u001B[0m               \u001B[32m ║");
            System.out.println("\u001B[32m╟──────┬───────────────────────────────╢");
            System.out.println("\u001B[32m║\u001B[34mOption\u001B[32m│         \u001B[34mDescription           \u001B[32m║");
            System.out.println("\u001B[32m╟──────┼───────────────────────────────╢");
            System.out.println("\u001B[34m║   1  \u001B[32m│ Show list Product            \u001B[34m ║");
            System.out.println("\u001B[34m║   2  \u001B[32m│ Product search by name       \u001B[34m ║");
            System.out.println("\u001B[34m║   3  \u001B[32m│ Add to cart                  \u001B[34m ║");
            System.out.println("\u001B[34m║   4  \u001B[32m│ View Cart                    \u001B[34m ║");
            System.out.println("\u001B[34m║   5  \u001B[32m│ Order History                \u001B[34m ║");
            System.out.println("\u001B[34m║   6  \u001B[32m│ Profile change               \u001B[34m ║");
            System.out.println("\u001B[34m║   7  \u001B[32m│ Change Password              \u001B[34m ║");
            System.out.println("\u001B[34m║   8  \u001B[32m│ Favorites List               \u001B[34m ║");
            System.out.println("\u001B[34m║   0  \u001B[32m│ Log Out                      \u001B[34m ║");
            System.out.println("\u001B[32m╚══════╧═══════════════════════════════╝");
            System.out.println("\u001B[33mEnter your choice:                          \u001B[0m");

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
                case 0:
                    logOut();
                    break;
                default:
                    System.err.println("please enter number from 1 to 8");
            }
            if (choice == 0) {
                break;
            }
        }
    }

    public static void profileChange(int id) {
        User userLogin = userController.findById(id);
        System.out.println("+----------------------------------+");
        System.out.println("|         Personal Information     |"); // thông tin cá nhân
        System.out.println("+----------------------------------+");
        System.out.println("| Name       | " + userLogin.getName() + "             |");
        System.out.println("| Email      | " + userLogin.getEmail() + "   |");
        System.out.println("| Phone      | " + userLogin.getPhoneNumber() + "          |");
        System.out.println("+----------------------------------+");
        System.out.println("| Do you want to change your       |");// bạn có muốn thay đổi thông tin không ?
        System.out.println("| information?                     |");
        System.out.println("| Enter Yes to make changes        |"); // yes để đồng ý thay đổi
        System.out.println("| --- No to escape                 |"); // no để thoát
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
            System.out.println("Enter Name:");
            userEdit.setName(InputMethods.getString());
            System.out.println("Enter Email");
            userEdit.setEmail(InputMethods.getEmail());
            userEdit.setPassword(userLogin.getPassword());
            System.out.println("Enter PhoneNumber ");
            userEdit.setPhoneNumber((InputMethods.getPhoneNumber()));
            System.out.println("Enter Yes to make changes --- No escape");//nhập yes để xác nhận thay đổi -----No hủy bỏ
            String confirmChoice = InputMethods.getString();
            if (confirmChoice.trim().toLowerCase().equals("yes")) {
                userController.save(userEdit);
                System.out.println("successful change"); // thay đổi thành công
            } else {
                System.out.println(" change has been canceled"); // hủy bỏ thay đổi
            }

        }
    }

    public static void menuAdmin() {
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║              Menu-Admin             \u001B[0m ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│      Account Manager       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│      Catalog Manager       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│      Product Manager       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m4    \u001B[34m│      Order Manager         \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m0    \u001B[34m│      Logout                \u001B[0m ║");
            System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
            System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");

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
                    System.err.println("please enter number from 1 to 4");
            }
            if (choice == 0) {
                break;
            }
        }
    }

    public static void menuAccountManager() {
        System.out.println("\u001B[32m╔══════════════════════════════════════╗");
        System.out.println("\u001B[32m║          Menu-Account-Manager       \u001B[0m ║");
        System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
        System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│      Show All Account      \u001B[0m ║");
        System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│   Block/Unblock Account    \u001B[0m ║");
        System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│            Back            \u001B[0m ║");
        System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
        System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");


    }

    public static void menuCart() {
        System.out.println("\u001B[32m╔══════════════════════════════════════╗");
        System.out.println("\u001B[32m║              Menu-Cart              \u001B[0m ║");
        System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
        System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│         Show Cart          \u001B[0m ║");
        System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│         Change Quantity    \u001B[0m ║");
        System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│         Delete Item        \u001B[0m ║");
        System.out.println("\u001B[34m║   \u001B[32m4    \u001B[34m│         Delete All         \u001B[0m ║");
        System.out.println("\u001B[34m║   \u001B[32m5    \u001B[34m│         Check out          \u001B[0m ║");
        System.out.println("\u001B[34m║   \u001B[32m6    \u001B[34m│         Back               \u001B[0m ║");
        System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
        System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");

    }

    public static void login() {
        System.out.println("-------------Sign-In-------------");
        System.out.println("Enter username");
        String username = InputMethods.getUseName();
        System.out.println("Enter password");
        String password = InputMethods.getPassword();
        // kiem tra dang nhap
        User user = userController.login(username, password);
        if (user == null) {
            System.err.println("Login failed"); // đăng nhập thất bại
        } else {
            if (user.getRoles().contains(RoleName.ADMIN)) {
                userLogin = user;
                menuAdmin();
            } else {
                if (user.isStatus()) {
                    userLogin = user;
                    menuUser();
                } else {
                    System.err.println("Your account is blocked"); //  tài khoản của bạn đã bị khóa
                    login();
                }
            }
        }

    }

    public static void register() {
        System.out.println("-------------Register-------------");
        User user = new User();
        user.setId(userController.getNewId());
        System.out.println("ID : " + user.getId());
        System.out.println("Enter Name");
        user.setName(InputMethods.getUseName());
        System.out.println("Enter Email");
        user.setEmail(InputMethods.getEmail());
        System.out.println("Enter phone");
        user.setPhoneNumber(InputMethods.getPhoneNumber());
        System.out.println("Enter Username");
        user.setUsername(InputMethods.getUseName());
        System.out.println("Enter Password");
        user.setPassword(InputMethods.getPassword());
//        System.out.println("Enter Roles: (etc: user,admin,...)");
//        String roles = InputMethods.getString();
//        String[] stringRoles = roles.split(",");
//        List<String> listRoles = Arrays.asList(stringRoles);
//        for (String r : stringRoles) {
//            // loi dụng co che break
//            switch (r) {
//                case "admin":
//                    user.getRoles().add(RoleName.ADMIN);
//                case "manager":
//                    user.getRoles().add(RoleName.MANAGER);
//                case "user":
//                    user.getRoles().add(RoleName.USER);
//                default:
//                    user.getRoles().add(RoleName.USER);
//            }
//        }

//        user.getRoles() = listRoles.stream().map(
//                r->{
//                    //
//                }
//        ).collect(Collectors.toList());
        for (User u : userController.findAll()) {
            if (u.getUsername().equals(user.getUsername()) && u.getPhoneNumber().equals(user.getPhoneNumber()) && u.getEmail().equals(user.getEmail())) {
                System.err.println("Account already exists"); // tài khoản đã tồn tại
                return;
            }
        }
        userController.save(user);
        System.out.println("\u001B[34m" + "Sign Up Success" + "\u001B[0m");
        System.out.println("\u001B[34m" + "please log in" + "\u001B[0m");
        login();

    }

    public static void logOut() {
        userLogin = null;
        menuStore();
        // con tro ddung
    }


}
