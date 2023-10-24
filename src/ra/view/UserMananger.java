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
                    System.err.println("vui lòng nhập số từ 1 đến 3");
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

        System.out.println("Nhập ID tài khoản:");
        int id = InputMethods.getInteger();
        User user = userController.findById(id);

        if (user == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }

        if (user.getId() == Navbar.userLogin.getId()) {
            System.err.println("Không thể chặn/bỏ chặn tài khoản quản trị viên.");
            return;
        }

        user.setStatus(!user.isStatus());
        String statusMessage = user.isStatus() ? "Mở khóa" : "Khóa";
        System.out.println("\u001B[34m" + statusMessage + " Tài khoản" + "\u001B[0m");
        userController.save(user);
    }
    public static void changePassword() {
        System.out.println("Mật khẩu cũ :" + " ");
        String ip = InputMethods.getString();
        if (userLogin.getPassword().equals(ip)) {
            userLogin.setId(userLogin.getId());
            userLogin.changePassword();
            System.out.println("Thay đổi thành công");
        } else {
            System.err.println("Mật khẩu không khớp");
            changePassword();
        }
    }

}
