package com.bridgelabz.BookStoreApp.DTO;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OrderDTO {

    @NotNull
    private Double quantity;

    @NotEmpty(message = "please provide address")
    private String address;

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;


    private Boolean cancel;

    @NotNull
    private Double price;
}
