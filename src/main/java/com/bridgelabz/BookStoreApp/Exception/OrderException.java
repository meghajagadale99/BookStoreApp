package com.bridgelabz.BookStoreApp.Exception;

public class OrderException extends RuntimeException {
    public OrderException.ExceptionTypes exceptionTypes;

    public OrderException(OrderException.ExceptionTypes exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }

    public enum ExceptionTypes {
        BOOK_QUANTITY_EXCEEDED("Requested quantity is not available"),
        ORDER_NOT_EXISTS("Order Doesn't Exists ...!");
        public String errorMessage;

        ExceptionTypes(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
