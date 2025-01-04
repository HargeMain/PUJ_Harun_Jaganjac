package org.harunjaganjac.example.controllers;

import org.harunjaganjac.example.models.User;
import org.harunjaganjac.example.response.RegisterResponse;
import org.harunjaganjac.example.services.UserService;

import java.util.List;

public final class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    public RegisterResponse createUser(String username, String email, String password, String role) {
        return userService.registerUserWithRole(username, email, password, role);
    }
    public RegisterResponse resetPassword( String username,String oldPassword, String newPassword) {
        return userService.resetPassword(username, oldPassword, newPassword);
    }
    public boolean deleteUser(String id) {
        return userService.deleteUser(id);
    }
    public User login(String username, String password) {
        return userService.login(username, password);
    }
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
