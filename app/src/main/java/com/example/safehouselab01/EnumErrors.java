package com.example.safehouselab01;


public enum EnumErrors {
    NAME("Name can not be empty"),
    EMAIL("Email is incorrect"),
    PASSWORD("Password is too short"),
    REPEAT_PASSWORD("Password doesn't match");

    private String ErrorMessage;

    EnumErrors(String error) {
        this.ErrorMessage = error;
    }

    public String getError() {
        return ErrorMessage;
    }
}
