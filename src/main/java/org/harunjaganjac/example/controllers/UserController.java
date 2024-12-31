package org.harunjaganjac.example.controllers;

import org.harunjaganjac.example.models.User;
import org.harunjaganjac.example.services.UserService;

import java.util.List;

public final class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    public boolean createUser(String username, String email, String password, String role) {
        return userService.registerUserWithRole(username, email, password, role);
    }
    public boolean resetPassword( String username, String newPassword) {
        return userService.resetPassword(username,newPassword);
    }
    public boolean deleteUser(String username) {
        return userService.deleteUser(username);
    }
    public User login(String username, String password) {
        return userService.login(username, password);
    }
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
