package com.musicstore.musicstore.exception;

public class UserNotPermitionException extends NotFoundException{
    public UserNotPermitionException(String message) {
        super(message);
    }
}
