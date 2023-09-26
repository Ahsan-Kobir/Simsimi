package com.akapps.simsimi.Models;

public class Message {
    private int type;
    private String message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
