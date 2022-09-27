package com.bridgelabz.BookStoreApp.Model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Component
@Entity
public class CartDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long CartId;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private UserData userData;

    @ManyToOne()
    @JoinColumn(name = "bookId")
    private BookDetailsModel bookDetailsModel;
    private Double quantity;

    public CartDetailsModel(Long cartId, UserData userData, BookDetailsModel bookDetailsModel, Double quantity) {
        CartId = cartId;
        this.userData = userData;
        this.bookDetailsModel = bookDetailsModel;
        this.quantity = quantity;
    }

    public CartDetailsModel(UserData userData, BookDetailsModel bookDetailsModel, Double quantity) {
        this.userData = userData;
        this.bookDetailsModel = bookDetailsModel;
        this.quantity = quantity;
    }

    public CartDetailsModel() {
    }
}