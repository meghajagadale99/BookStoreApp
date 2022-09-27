package com.bridgelabz.BookStoreApp.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
public class CartDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;

    @NotNull
    private Double quantity;

}