package com.bridgelabz.BookStoreApp.Model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class OrderDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private LocalDate orderDate = LocalDate.now();
    private Double price;
    private Double quantity;
    private String address;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserData user;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookDetailsModel bookDetailsModel;

    private Boolean cancel;

    public OrderDetailsModel() {
        super();
    }

    public OrderDetailsModel(Long orderId, Double price, Double quantity, String address, UserData user,
                             BookDetailsModel bookDetailsModel, Boolean cancel) {
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
        this.address = address;
        this.user = user;
        this.bookDetailsModel = bookDetailsModel;
        this.cancel = cancel;
    }

    public OrderDetailsModel(Double price, Double quantity, String address, UserData user,
                             BookDetailsModel bookDetailsModel, Boolean cancel) {
        this.price = price;
        this.quantity = quantity;
        this.address = address;
        this.user = user;
        this.bookDetailsModel = bookDetailsModel;
        this.cancel = cancel;
    }
}
