package com.vijay.sidhu.internal.model;

public class Message {

    private int id;
    private String message;

    public Message(){}

    public Message(int id, String message){
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }
}
