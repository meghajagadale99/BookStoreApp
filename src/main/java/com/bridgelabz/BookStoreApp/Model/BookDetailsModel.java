package com.bridgelabz.BookStoreApp.Model;

import com.bridgelabz.BookStoreApp.DTO.BookDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Component
@Entity
@Table
public class BookDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private Long userId;
    private String bookName;
    private String authorName;
    private String description;
    private double bookPrice;
    private double quantity;
    private int rating;
    private int publishingYear;

    private LocalDateTime createdAt = LocalDateTime.now();


    public BookDetailsModel(Long bookId, String bookName, String authorName, String description, double bookPrice, double quantity, int rating, int publishingYear, Long userId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.description = description;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.rating = rating;
        this.publishingYear = publishingYear;
        this.userId = userId;

    }

    public BookDetailsModel() {
    }

    public BookDetailsModel(BookDTO bookDTO) {
        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.description = bookDTO.getDescription();
        this.bookPrice = bookDTO.getBookPrice();
        this.quantity = bookDTO.getQuantity();
        this.rating = bookDTO.getRating();
        this.publishingYear = bookDTO.getPublishingYear();

    }


    public void updateBookDetails(BookDTO bookDTO) {
        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.description = bookDTO.getDescription();
        this.bookPrice = bookDTO.getBookPrice();
        this.quantity = bookDTO.getQuantity();
        this.rating = bookDTO.getRating();
        this.publishingYear = bookDTO.getPublishingYear();
    }
}