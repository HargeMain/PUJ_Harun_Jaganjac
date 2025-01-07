package org.harunjaganjac.example.response;

import org.harunjaganjac.example.models.User;

public final class RegisterResponse extends BaseResponse {
   private final User user;

    public RegisterResponse(String message, boolean success,User user) {
        super(message, success);
        this.user = user;
    }
    // Getters and Setters --START--


    public User getUser() {
        return user;
    }
    // Getters and Setters --END--


}
