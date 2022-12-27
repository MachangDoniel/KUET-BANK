package com.example.kuetbank;

public class Transaction{
    String message;

    public Transaction(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
