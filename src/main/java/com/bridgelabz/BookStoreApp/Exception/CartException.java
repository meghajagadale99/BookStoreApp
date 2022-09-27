package com.bridgelabz.BookStoreApp.Exception;

public class CartException extends RuntimeException {
    public ExceptionTypes exceptionTypes;

    public CartException(ExceptionTypes exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }

    public enum ExceptionTypes {
        BOOK_ALREADY_PRESENT("book exists in cart"), CART_ITEM_NOT_FOUND("book not present in cart");
        public String errorMessage;

        ExceptionTypes(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
