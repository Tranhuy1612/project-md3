package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.CartController;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.model.CartItem;
import ra.model.Order;
import ra.model.Product;
import ra.model.User;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private List<Product> favorites = new ArrayList<>();
    private static CartController cartController;
    public ProductController productController;

    public CartManager() {
        productController = new ProductController();
        cartController = new CartController(Navbar.userLogin);
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
                    System.err.println("please enter number from 1 to 5");
            }

        }
    }

    public void showCart() {
        User userLogin = Navbar.userLogin;
        if (userLogin.getCart().isEmpty()) {
            System.err.println("Cart is Empty");
            return;
        }
        for (CartItem ci : userLogin.getCart()
        ) {
            ci.setProduct(productController.findById(ci.getProduct().getId()));
            System.out.println(ci);
        }
    }

    public void changeQuantity() {
        System.out.println("Enter cartitemId");
        int cartItemID = InputMethods.getInteger();
        CartItem cartItem = cartController.findById(cartItemID);
        if (cartItem == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        System.out.println("Enter Quantity");
        cartItem.setQuantity(InputMethods.getInteger());
        cartController.save(cartItem);

    }

    public void deleteItem() {
        System.out.println("Enter cartitemId");
        int cartItemID = InputMethods.getInteger();
        if (cartController.findById(cartItemID) == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        cartController.delete(cartItemID);
    }

    public void checkout(ProductController productController) {
        OrderController orderController = new OrderController();
        User userLogin = Navbar.userLogin;
        if (userLogin.getCart().isEmpty()) {
            System.err.println("Cart is Empty");
            return;
        }
        //  kiểm tra số lượng trong kho
        for (CartItem ci : userLogin.getCart()) {
            Product p = productController.findById(ci.getProduct().getId());
            if (ci.getQuantity() > p.getStock()) {
                System.err.println(" Product " + p.getName() + " only " + p.getStock() + "products, please reduce the quantity ");//sản phẩm, vui lòng giảm số lượng
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

        newOrder.setUserId(userLogin.getId());
        System.out.println("Enter Name");
        newOrder.setReceiver(InputMethods.getUseName());
        System.out.println("Ennter Phone number");
        newOrder.setPhoneNumber(InputMethods.getPhoneNumber());
        System.out.println("Enter address");
        newOrder.setAddress(InputMethods.getString());
        System.out.println("\u001B[34m" + "Payment success" + "\u001B[0m");
        orderController.save(newOrder);
        // giảm số lượng đi
        for (CartItem ci : userLogin.getCart()) {
            Product p = productController.findById(ci.getProduct().getId());
            p.setStock(p.getStock() - ci.getQuantity());
            productController.save(p);
        }
        cartController.clearAll();
    }


    public static void addToCart() {
        cartController = new CartController(Navbar.userLogin);
        ProductController productController = new ProductController();
        System.out.println("Enter Product id");
        int proId = InputMethods.getInteger();
        Product pro = productController.findById(proId);
        if (pro == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        CartItem cartItem = new CartItem();
        cartItem.setId(cartController.getNewId());
        cartItem.setProduct(pro);
        System.out.println("Enter quantity");
        cartItem.setQuantity(InputMethods.getInteger());
        cartController.save(cartItem);
    }
}
