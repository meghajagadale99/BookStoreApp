package com.bridgelabz.BookStoreApp.Exception;

import com.bridgelabz.BookStoreApp.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartExceptionHandler {
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ResponseDTO> handleCartException(
            CartException cartException) {
        return new ResponseEntity<>(new ResponseDTO(cartException.exceptionTypes.errorMessage,
                null),
                HttpStatus.BAD_REQUEST);
    }
}