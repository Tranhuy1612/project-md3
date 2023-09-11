package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.UserController;
import ra.model.User;


import static ra.view.Navbar.userLogin;

public class UserMananger {
    private UserController userController;

    public UserMananger(UserController userController) {
        this.userController = userController;
        while (true) {
            Navbar.menuAccountManager();
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // hiển thị tất cả tài khoản
                    showAllAccount();
                    break;
                case 2:
                    // thay đổi trạng thái ( khóa / mở)
                    changeStatus();
                    break;
                case 3:
                    Navbar.menuAdmin();
                    break;
                default:
                    System.err.println("please enter number from 1 to 3");
            }
        }
    }
    public void showAllAccount() {
        for (User u : userController.findAll()) {
            System.out.println("-------------------------------------------");
            System.out.println(u);
        }
    }
    public void changeStatus() {
        // lấy ra userlogin để check quyền xem có được quyền khóa tài khoản kia không
//        System.out.println("Enter Account ID");//nhập id
//        int id = InputMethods.getInteger();
//        User user = userController.findById(id);
//        if (user == null) {
//            System.err.println(Constants.NOT_FOUND);
//            return;
//        }
//        if (user.isStatus() == false) {
//            System.out.println("\u001B[34m" + "Un lock account " + "\u001B[0m");
//            user.setStatus(true);
//            userController.save(user);
//        } else {
//            user.setStatus(!user.isStatus());
//            System.out.println("\u001B[34m" + "lock account" + "\u001B[0m");
//            userController.save(user);
//        }
        System.out.println("Enter Account ID:");
        int id = InputMethods.getInteger();
        User user = userController.findById(id);

        if (user == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }

        if (user.getId() == Navbar.userLogin.getId()) {
            System.err.println(" Cannot block/unblock admin account.");
            return;
        }

        user.setStatus(!user.isStatus());
        String statusMessage = user.isStatus() ? "Unlocked" : "Locked";
        System.out.println("\u001B[34m" + statusMessage + " account" + "\u001B[0m");
        userController.save(user);
    }
    public static void changePassword() {
        System.out.println("Old password :" + " ");
        String ip = InputMethods.getString();
        if (userLogin.getPassword().equals(ip)) {
            userLogin.setId(userLogin.getId());
            userLogin.changePassword();
            System.out.println(" successful change"); // thay đổi thành công
        } else {
            System.err.println("Password not match "); // Mật khẩu không khớp
            changePassword();
        }
    }

}
