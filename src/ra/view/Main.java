package ra.view;

import ra.model.Product;
import ra.model.RoleName;
import ra.model.User;
import ra.service.UserService;
import ra.util.DataBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
//        Set<RoleName> set = new HashSet<>();
        Set<RoleName> set2 = new HashSet<>();
//        set2.add(RoleName.USER);
        set2.add(RoleName.ADMIN);
//        set.add(RoleName.USER);
//        User user = new User(2, "Huy", "huyth", "123456", "HaNam", "0987654321", true, set, "huy@gamil.com");
        User admin = new User();
        admin.setId(1);
        admin.setStatus(true);
        admin.setName("admin");
        admin.setUsername("admin12");
        admin.setPassword("admin12");
        admin.setRoles(set2);
        userService.save(admin);
//        userService.save(user);
        DataBase<User> data = new DataBase<>();
        for (User u : data.readFromFile(DataBase.USER_PATH)) {
            System.out.println("-------------------------------------------");
            System.out.println(u);
        }

////         fix cứng sp
//        List<Product> list = new ArrayList<>();
//        Product p1 = new Product(1, "Trà sữa thái", 20000, "bestseller", 100, true);
//        Product p2 = new Product(2, "Trà sữa trân châu", 30000, "béo , ngậy ", 100, true);
//        Product p3 = new Product(3, "Trà nhài", 20000, "thơm", 100, true);
//        Product p4 = new Product(4, "Trà chanh", 15000, "thanh mát", 100, true);
//        Product p5 = new Product(5, "Trà chanh Lô hội", 25000, "ngon", 100, true);
//        Product p6 = new Product(6, "Kem socola", 15000, "ngon", 100, true);
//        Product p7 = new Product(7, "Kem ốc quế", 15000, "ngon", 100, true);
//        Product p8 = new Product(8, "Nước chanh tươi", 10000, "mát", 100, true);
//        list.add(p1);
//        list.add(p2);
//        list.add(p3);
//        list.add(p4);
//        list.add(p5);
//        list.add(p6);
//        list.add(p7);
//        list.add(p8);
//        new DataBase<Product>().writeToFile(list, "C:\\Users\\admin\\Desktop\\ProjectMd3-Huy\\src\\ra\\util\\products.txt");
    }

}
