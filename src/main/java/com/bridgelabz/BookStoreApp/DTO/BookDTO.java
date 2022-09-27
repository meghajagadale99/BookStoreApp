package com.bridgelabz.BookStoreApp.DTO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class BookDTO {

    @NotBlank(message = "Please Enter BookName!")
    public String bookName;
    @NotBlank(message = "Please Enter AuthorName!")
    public String authorName;
    @NotBlank(message = "Please Write Description!")
    public String description;
    @NotNull
    public int rating;
    @NotNull
    public double bookPrice;
    @NotNull
    public double quantity;
    @NotNull
    public int publishingYear;

    public BookDTO(String bookName, String authorName, String description, int rating, double bookPrice, double quantity,
                   int publishingYear) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.description = description;
        this.rating = rating;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.publishingYear = publishingYear;

    }
}
