package org.harunjaganjac.example.response;

public abstract class BaseResponse {
    private final String message;
    private final boolean success;

    public BaseResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters and Setters --START--
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
    // Getters and Setters --END--
}
