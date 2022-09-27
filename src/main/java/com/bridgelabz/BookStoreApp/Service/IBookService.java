package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.DTO.BookDTO;
import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookService {

    List<BookDetailsModel> getAllBooks();

    BookDetailsModel getBookById(Long id);

    BookDetailsModel addBook(BookDTO bookDTO, String auth);

    String deleteBookById(Long bookId);

    BookDetailsModel updateBookById(Long bookId, String auth, BookDTO bookDTO);

    List<BookDetailsModel> searchByName(String name);

    int getCountOfBooks();

    List<BookDetailsModel> getBooksWithPublishingYear();

    List<BookDetailsModel> getBooksByNewLaunch();

    List<BookDetailsModel> getBooksWithIncreasingOrderOfTheirPrice();

    List<BookDetailsModel> getBooksWithDecreasingOrderOfTheirPrice();
}