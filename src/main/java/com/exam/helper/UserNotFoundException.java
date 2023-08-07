package com.exam.helper;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);
    }
}
