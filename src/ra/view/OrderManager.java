package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.model.CartItem;
import ra.model.Order;
import ra.model.Product;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private OrderController orderController;

    public OrderManager() {
        orderController = new OrderController();
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║            Order-History            \u001B[0m ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│   Show all order           \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│   Show order waiting       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│   Show order accepted      \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m4    \u001B[34m│   Show order canceled      \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m5    \u001B[34m│   Show order detail        \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m0    \u001B[34m│   Back                     \u001B[0m ║");
            System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
            System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // hiển thị tất cả đơn hàng
                    showAllOrder();
                    break;
                case 2:
                    // chờ xác nhận
                    showOrderByCode((byte) 0);
                    break;
                case 3:
                    showOrderByCode((byte) 1);
                    break;
                case 4:
                    showOrderByCode((byte) 2);
                    break;
                case 5:
                    // chi tiết hóa đơn
                    showOrderDetail();
                    break;
                case 0:
                    Navbar.menuUser();
                    break;
                default:
                    System.err.println("please enter number from 1 to 5");
            }
        }
    }

    public void showAllOrder() {
        List<Order> list = orderController.findOrderByUserId();
        if (list.isEmpty()) {
            System.err.println("History is empty");
            return;
        }
        for (Order o : list) {
            System.out.println(o);
        }
    }

    public void showOrderByCode(byte code) {
        List<Order> orders = orderController.findOrderByUserId();
        List<Order> filter = new ArrayList<>();
        for (Order o : orders) {
            if (o.getStatus() == code) {
                filter.add(o);
            }
        }
        if (filter.isEmpty()) {
            System.err.println("order is empty");
            return;
        }
        for (Order o : filter) {
            System.out.println(o);
        }
    }

    public void showOrderDetail() {
        ProductController productController = new ProductController();
        System.out.println("Enter order ID");
        int orderId = InputMethods.getInteger();
        Order order = orderController.findById(orderId);
        if (order == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        // in ra chi tiết hóa đơn
        System.out.printf("---------------------OrderDetail-----------------------\n");
        System.out.printf("                    Id:%5d                              \n", order.getId());
        System.out.println("--------------------Infomation--------------------------");
        System.out.print("Receiver: " + order.getReceiver() + "| Phone : " + order.getPhoneNumber() + "\n");
        System.out.println("Address : " + order.getAddress());
        System.out.println("--------------------Detail-------------------------------");
        for (CartItem ci : order.getOrderDetail()) {
            System.out.println(ci);
        }
        System.out.println("Total : " + order.getTotal());
        System.out.println("------------------------End------------------------------");
        if (order.getStatus() == 0) {
            System.out.println("Do you want to cancel this order?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("Enter your choice");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                // hủy
                for (CartItem ci : order.getOrderDetail()) {
                    Product p = productController.findById(ci.getProduct().getId());
                    p.setStock(p.getStock() + ci.getQuantity());
                    productController.save(p);
                }
                order.setStatus((byte) 2);
                orderController.save(order);
            }
        }
    }

    public OrderManager(OrderController orderController) {
        this.orderController = orderController;
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════╗");
            System.out.println("\u001B[32m║            Order-Manager            \u001B[0m ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────╢");
            System.out.println("\u001B[34m║   \u001B[32m1    \u001B[34m│   ShowOrderUser            \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m2    \u001B[34m│   Order confirmation       \u001B[0m ║");
            System.out.println("\u001B[34m║   \u001B[32m3    \u001B[34m│   Back                     \u001B[0m ║");
            System.out.println("\u001B[33m╚════════╧═════════════════════════════╝");
            System.out.println("\u001B[33mEnter your choice:                       \u001B[0m");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showOrderUser();
                    break;
                case 2:
                    OrderConfirm();
                    break;
                case 3:
                    Navbar.menuAdmin();
                    break;
                default:
                    System.err.println("please enter number from 1 to 3");
            }
        }
    }
    public void showOrderUser() {
        for (Order od : orderController.findAll()
        ) {
            System.out.println("\u001B[34m ————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
            System.out.println(od);
            System.out.println("\u001B[34m ————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
        }
    }
    public void OrderConfirm() {
        System.out.println("Enter order number"); //nhập mã đơn hàng
        int ip = InputMethods.getInteger();
        Order comfirmOrder = orderController.findALlById(ip);
        if (comfirmOrder == null) {
            System.err.println("Can't find order number");//không tìm thấy mã đơn hàng
        } else if (comfirmOrder.getStatus() == 0) {
            System.out.println("Do you want to confirm this order?");//Bạn muốn xác nhận đơn hàng này chứ ?
            System.out.println("1. Yes");
            System.out.println("2. No ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                System.out.println("Confirmed");//Đã xác nhận
                comfirmOrder.setStatus((byte) 1);
                orderController.save(comfirmOrder);
            }
        } else if (comfirmOrder.getStatus() == 1) {
            System.err.println("This menu has been confirmed."); // Đơn hàng này đã được xác nhận
        }
    }
}
