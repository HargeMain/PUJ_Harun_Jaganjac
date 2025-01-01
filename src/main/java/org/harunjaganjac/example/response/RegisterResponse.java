package org.harunjaganjac.example.response;

import org.harunjaganjac.example.models.User;

public final class RegisterResponse {
    private final String message;
    private final boolean success;
    private final User user;

    public RegisterResponse(String message, boolean success,User user) {
        this.message = message;
        this.success = success;
        this.user = user;
    }
    // Getters and Setters --START--
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }
    // Getters and Setters --END--


}
