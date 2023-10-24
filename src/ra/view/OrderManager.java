package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.model.*;

import java.util.ArrayList;
import java.util.List;


public class OrderManager {
    private OrderController orderController;

    public OrderManager() {
        orderController = new OrderController();
        while (true) {
            System.out.println("\u001B[32m╔══════════════════════════════════════════╗");
            System.out.println("\u001B[32m║            Order-History                 ║");
            System.out.println("\u001B[32m╟────────┬─────────────────────────────────╢");
            System.out.println("\u001B[32m║   1    │ Hiển thị tất cả đơn hàng        ║");
            System.out.println("\u001B[32m║   2    │ Hiển thị đơn hàng đang chờ      ║");
            System.out.println("\u001B[32m║   3    │ Hiển thị đơn hàng được chấp nhận║");
            System.out.println("\u001B[32m║   4    │ Hiển thị đơn hàng đã bị hủy     ║");
            System.out.println("\u001B[32m║   5    │ Hiển thị chi tiết đơn hàng      ║");
            System.out.println("\u001B[32m║   6    │ Gưỉ phản hồi                    ║");
            System.out.println("\u001B[32m║   0    │ Trở về                          ║");
            System.out.println("\u001B[32m╚════════╧═════════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:                               ");
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
                case 6:    // gửi phản hồi về sản phẩm
                    giveAFeedback();
                    break;
                case 0:
                    Navbar.menuUser();
                    break;
                default:
                    System.err.println("vui lòng nhập số từ 1 đến 5");
            }
        }
    }

    public void giveAFeedback() {
        List<Order> orderList;
        if (Navbar.userLogin.getRoles().contains(RoleName.ADMIN)) {
            orderList = orderController.findAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == 1) {
                filter.add(order);
            }
        }
        if (filter.isEmpty()) {
            System.err.println("Không có sản phẩm . Nên chưa được phản hồi ");
            return;
        }
        System.out.println("Nhập ID đơn hàng: ");
        int feedbackOrderId = InputMethods.getInteger();
        Order feedbackOrder = find(feedbackOrderId, filter);
        if (feedbackOrder == null) {
            System.err.println("Không tìm thấy");
            return;
        }
        System.out.println("Nhập phản hồi: ");
        Feedback feedback = new Feedback();
        feedback.setFeedbackContent(InputMethods.getString());
        feedbackOrder.getFeedbackList().add(feedback);
        orderController.save(feedbackOrder);
        System.out.println("\u001B[1;35mCảm ơn phản hồi của bạn!\u001B[0m");
    }

    public Order find(int id, List<Order> orderList) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public void showAllOrder() {
        List<Order> list = orderController.findOrderByUserId();
        if (list.isEmpty()) {
            System.err.println("Lịch sử trống rỗng");
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
            System.err.println("đơn hàng trống ");
            return;
        }
        for (Order o : filter) {
            System.out.println(o);
        }
    }

    public void showOrderDetail() {
        ProductController productController = new ProductController();
        System.out.println("Nhập ID đơn hàng :");
        int orderId = InputMethods.getInteger();
        Order order = orderController.findById(orderId);
        if (order == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        // in ra chi tiết hóa đơn
        System.out.printf("---------------------Chi tiết đặt hàng-----------------------\n");
        System.out.printf("                    Id:%5d                              \n", order.getId());
        System.out.println("--------------------Thông tin--------------------------");
        System.out.print("Người nhận: " + order.getReceiver() + "| Số điện thoại : " + order.getPhoneNumber() + "\n");
        System.out.println("Địa chỉ : " + order.getAddress());
        System.out.println("--------------------Chi tiết-------------------------------");
        for (CartItem ci : order.getOrderDetail()) {
            System.out.println(ci);
        }
        System.out.println("Tổng : " + order.getTotal());
        System.out.println("------------------------Kết thúc------------------------------");
        if (order.getStatus() == 0) {
            System.out.println("Bạn có muốn hủy đơn hàng này không?");
            System.out.println("1. Có");
            System.out.println("2. Không ");
            System.out.println("Nhập lựa chọn của bạn ");
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
            System.out.println("\u001B[32m╔════════════════════════════════════════════════╗");
            System.out.println("\u001B[32m║            Order-Manager                       ║");
            System.out.println("\u001B[32m╟────────┬───────────────────────────────────────╢");
            System.out.println("\u001B[32m║   1    │ Hiển thị đơn đặt hàng của người dùng  ║");
            System.out.println("\u001B[32m║   2    │ Xác nhận đơn hàng                     ║");
            System.out.println("\u001B[32m║   3    │ Xem phản hồi                          ║");
            System.out.println("\u001B[32m║   4    │ Trở về                                ║");
            System.out.println("\u001B[32m╚════════╧═══════════════════════════════════════╝");
            System.out.println("Nhập lựa chọn của bạn:                                      ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showOrderUser();
                    break;
                case 2:
                    OrderConfirm();
                    break;
                case 3: // xem phản hồi
                    viewFeedback();
                    break;
                case 4:
                    Navbar.menuAdmin();
                    break;
                default:
                    System.err.println("vui lòng nhập số từ 1 đến 3");
            }
        }
    }

    public void viewFeedback() {
        List<Order> orderList = orderController.findAll();
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == 1 && !order.getFeedbackList().isEmpty()) {
                filter.add(order);
            }
        }
        if (filter.isEmpty()) {
            System.err.println("Không có phản hồi ");
            return;
        }
        for (Order order : filter) {
            UserController userController = new UserController();
            User orderUser = userController.findById(order.getUserId());
            List<String> itemName = new ArrayList<>();
            for (CartItem item : order.getOrderDetail()) {
                itemName.add(item.getProduct().getName());
            }
            System.out.println("---------------------------------------" + "\n" +
                    "ID đơn hàng: " + order.getId() + " | Khách hàng: " + orderUser.getName() + "\n" +
                    "Đã đặt hàng: " + itemName.toString().replace("[", "").replace("]", "") + "\n" +
                    "Phản hồi   : " + order.getFeedbackList().toString().replace("[", "").replace("]", ""));

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
        System.out.println(" Nhập mã đơn hàng :");
        int ip = InputMethods.getInteger();
        Order comfirmOrder = orderController.findALlById(ip);
        if (comfirmOrder == null) {
            System.err.println("không tìm thấy mã đơn hàng");
        } else if (comfirmOrder.getStatus() == 0) {
            System.out.println("Bạn muốn xác nhận đơn hàng này chứ ?");
            System.out.println("1. Yes");
            System.out.println("2. No ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                System.out.println("Đã xác nhận");
                comfirmOrder.setStatus((byte) 1);
                orderController.save(comfirmOrder);
            }
        } else if (comfirmOrder.getStatus() == 1) {
            System.err.println(" Đơn hàng này đã được xác nhận");
        }
    }
}
