package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.CartController;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.model.*;

import java.util.ArrayList;
import java.util.List;

import static ra.view.Navbar.userLogin;

public class CartManager {
    private List<Product> favorites = new ArrayList<>();
    private static CartController cartController;
    public ProductController productController;

    public CartManager() {
        productController = new ProductController();
        cartController = new CartController(userLogin);
        while (true) {
            Navbar.menuCart();
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // xem danh sách giỏ hàng
                    showCart();
                    break;
                case 2:
                    // chỉnh sửa số lượng
                    changeQuantity();
                    break;
                case 3:
                    // xóa 1 item
                    deleteItem();
                    break;
                case 4:
                    // xóa hêt
                    cartController.clearAll();
                    break;
                case 5:
                    // tạo hóa đơn
                    checkout(productController);
                    break;
                case 6:
                    Navbar.menuUser();
                    break;
                default:
                    System.err.println("vui lòng nhập số từ 1 đến 5");
            }

        }
    }

    public void showCart() {
        User userLogin = Navbar.userLogin;
        if (userLogin.getCart().isEmpty()) {
            System.err.println("Giỏ hàng rỗng");
            return;
        }
        for (CartItem ci : userLogin.getCart()
        ) {
            ci.setProduct(productController.findById(ci.getProduct().getId()));
            System.out.println(ci);
        }
    }

    public void changeQuantity() {
        System.out.println("Nhập Id giỏ hàng");
        int cartItemID = InputMethods.getInteger();
        CartItem cartItem = cartController.findById(cartItemID);
        if (cartItem == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        System.out.println("Nhập số lượng");
        cartItem.setQuantity(InputMethods.getInteger());
        cartController.save(cartItem);

    }

    public void deleteItem() {
        System.out.println("Nhập Id giỏ hàng");
        int cartItemID = InputMethods.getInteger();
        if (cartController.findById(cartItemID) == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        cartController.delete(cartItemID);
    }

    public void checkout(ProductController productController) {
        OrderController orderController = new OrderController();
        UserController userController = new UserController();
        User userLogin = Navbar.userLogin;
        if (userLogin.getCart().isEmpty()) {
            System.err.println("Giỏ hàng rỗng ");
            return;
        }
        //  kiểm tra số lượng trong kho
        for (CartItem ci : userLogin.getCart()) {
            Product p = productController.findById(ci.getProduct().getId());
            if (ci.getQuantity() > p.getStock()) {
                System.err.println(" Sản phẩm " + p.getName() + " chỉ " + p.getStock() + "sản phẩm , vui lòng giảm số lượng ");
                return;
            }
        }

        Order newOrder = new Order();
        newOrder.setId(orderController.getNewId());
        // coppy sp trong giỏ hàng sang hóa đơn
        newOrder.setOrderDetail(userLogin.getCart());
        // cập nhật tổng tiền
        double total = 0;
        for (CartItem ci : userLogin.getCart()) {
            total += ci.getQuantity() * ci.getProduct().getPrice();
        }
        newOrder.setTotal(total);
        System.out.println("Chọn phương thức thanh toán:");
        System.out.println("1. Tiền mặt");
        System.out.println("2. Thanh toán bằng ví điện tử");
        System.out.println("3. Trở về");
        int choice = InputMethods.getInteger();
        if (choice == 1) {
            newOrder.setUserId(userLogin.getId());
            System.out.println("Nhập tên :");
            newOrder.setReceiver(InputMethods.getUseName());
            System.out.println("Nhập số điện thoại :");
            newOrder.setPhoneNumber(InputMethods.getPhoneNumber());
            System.out.println("Nhập địa chỉ :");
            newOrder.setAddress(InputMethods.getString());
            System.out.println("\u001B[34m" + "Thanh toán thành công" + "\u001B[0m");
            newOrder.setPayment(RolePayment.CASH);
            orderController.save(newOrder);
            // giảm số lượng đi
            for (CartItem ci : userLogin.getCart()) {
                Product p = productController.findById(ci.getProduct().getId());
                p.setStock(p.getStock() - ci.getQuantity());
                productController.save(p);
            }
            userController.save(userLogin);
            cartController.clearAll();
        } else if (choice == 2) {
            if (userLogin.getWallet() < total) {
                System.err.println("Số tiền trong tài khoản không đủ để thanh toán");
            } else {
                newOrder.setUserId(userLogin.getId());
                System.out.println("Nhập tên : ");
                newOrder.setReceiver(InputMethods.getString());
                System.out.println("Nhập số điện thoai :");
                newOrder.setPhoneNumber(InputMethods.getPhoneNumber());
                System.out.println("Nhập địa chỉ :");
                newOrder.setAddress(InputMethods.getString());
                System.out.println("Thanh toán thành công");
                newOrder.setPayment(RolePayment.WALLET);
                orderController.save(newOrder);
                // giảm số lượng đi
                for (CartItem ci : userLogin.getCart()) {
                    Product p = productController.findById(ci.getProduct().getId());
                    p.setStock(p.getStock() - ci.getQuantity());
                    productController.save(p);
                }
                userLogin.setWallet(userLogin.getWallet() - total);
                userController.save(userLogin);
                cartController.clearAll();
            }
        } else if (choice == 3) {
            new CartManager();
        } else {
            System.err.println("Lựa chọn không hợp lệ");
        }
    }

    public static void addToCart() {
        cartController = new CartController(userLogin);
        ProductController productController = new ProductController();
        System.out.println("Nhập Id sản phẩm");
        int proId = InputMethods.getInteger();
        Product pro = productController.findById(proId);
        if (pro == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        int availableStock = pro.getStock();
        System.out.println("Nhập số lượng");
        int quantity = InputMethods.getInteger();
        if (quantity > availableStock) {
            System.err.println("số lượng trong kho không đủ . số lượng tồn kho : " + availableStock);
            return;
        }
        CartItem cartItem = new CartItem();
        cartItem.setId(cartController.getNewId());
        cartItem.setProduct(pro);
        cartItem.setQuantity(quantity);
        cartController.save(cartItem);
    }
}
