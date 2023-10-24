package ra.model;

import ra.config.InputMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String address;
    private String phoneNumber;
    private boolean status = true;
    private Set<RoleName> roles = new HashSet<>();
    private List<CartItem> cart = new ArrayList<>();
    private List<Integer> favorites = new ArrayList<>();
    private double wallet = 0;

    public User() {
    }


    public User(int id, String name, String email, String username, String password, String address, String phoneNumber, boolean status, Set<RoleName> roles, List<CartItem> cart, List<Integer> favorites) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.roles = roles;
        this.cart = cart;
        this.favorites = favorites;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }


    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void changePassword() {
        System.out.println("Mật khẩu mới là  :");
        this.password = InputMethods.getPassword();
    }

    public User(List<Integer> favorites) {
        this.favorites = favorites;
    }

    public List<Integer> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Integer> favorites) {
        this.favorites = favorites;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        String green = "\u001B[32m";

        String role;
        if (roles.contains(RoleName.ADMIN)) {
            role = "ADMIN";
        } else if (roles.contains(RoleName.MANAGER)) {
            role = "MANAGER";
        } else {
            role = "USER";
        }
        return green + "ID : " + id + " | Tên : " + name + "| Username : " + username + " | Role : " + role + "| Trạng thái : " + (status ? "Mở khóa" : "Khóa");
    }


}
