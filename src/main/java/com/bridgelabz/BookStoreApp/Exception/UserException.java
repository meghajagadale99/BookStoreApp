package com.bridgelabz.BookStoreApp.Exception;

public class UserException extends RuntimeException {
    public enum ExceptionType {
        USER_ALREADY_PRESENT,
        EMAIL_NOT_FOUND,
        PASSWORD_INVALID,
        ALREADY_VERIFIED,
        INVALID_DATA,
        USER_NOT_FOUND,
        OTP_INVALID,
        USER_UNAUTHORISED
    }

    public UserException.ExceptionType type;

    public UserException(String message, UserException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
