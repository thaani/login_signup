package com.example.login_signup;

public class MSG {
    private Integer success;
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public MSG() {
    }

    public MSG(Integer success, String message) {
        this.success = success;
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
