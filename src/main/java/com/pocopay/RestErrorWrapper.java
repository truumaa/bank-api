package com.pocopay;

public class RestErrorWrapper {

    private String message;

    public RestErrorWrapper(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
