package com.bridgelabz.BookStoreApp.Controller;

import com.bridgelabz.BookStoreApp.DTO.BookDTO;
import com.bridgelabz.BookStoreApp.DTO.ResponseDTO;
import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import com.bridgelabz.BookStoreApp.Service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/welcome")
    public String welcomeBook() {
        return "Hello in Online Book Store DashBoard";
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable("id") Long id) {
        BookDetailsModel bookDetailsModel = bookService.getBookById(id);
        ResponseDTO responseDTO = new ResponseDTO("Got Book By Id", bookDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<ResponseDTO> getAllBooks() {
        List<BookDetailsModel> bookDetailsModels = bookService.getAllBooks();
        ResponseDTO responseDTO = new ResponseDTO("Got All Books List", bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/addBook/")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody BookDTO bookDTO, @RequestHeader(value = "Authorization") String auth ) {
        System.out.println(bookDTO);
        BookDetailsModel bookDetailsModel = bookService.addBook(bookDTO, auth);
        ResponseDTO responseDTO = new ResponseDTO("Book Added Successfully", bookDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ResponseDTO> deleteBookById(@PathVariable Long bookId) {
        String bookName = bookService.deleteBookById(bookId);
        ResponseDTO responseDTO
                = new ResponseDTO("The book " + bookName + " with bookId " + bookId + " is deleted", bookName);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseDTO> updateBookById(@PathVariable Long bookId, @RequestHeader(value = "Authorization") String auth, @RequestBody BookDTO bookDTO) {
        BookDetailsModel bookDetailsModel = bookService.updateBookById(bookId, auth, bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("The book " + bookDetailsModel.getBookName() + " with book id " + bookId + " is updated ", bookDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/filterBySearch")
    public ResponseEntity<ResponseDTO> searchByName(@RequestParam String name) {
        List<BookDetailsModel> bookDetailsModels = bookService.searchByName(name);
        ResponseDTO responseDTO = new ResponseDTO("Books Filterd by search sequence", bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/books/count")
    public ResponseEntity<ResponseDTO> getTotalCount() {
        int count = bookService.getCountOfBooks();
        return new ResponseEntity<>(new ResponseDTO("The books count is : ", count), HttpStatus.OK);
    }

    @GetMapping("/getBook sByPublishingYear")
    public ResponseEntity<ResponseDTO> getBooksWithPublishingYear() {
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksWithPublishingYear();
        ResponseDTO responseDTO = new ResponseDTO("Showing books according to publishing year",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getBooksByNewLaunch")
    public ResponseEntity<ResponseDTO> getBooksByNewLaunch() {
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksByNewLaunch();
        ResponseDTO responseDTO = new ResponseDTO("Showing books according to New Launch",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getBookAscending")
    public ResponseEntity<ResponseDTO> getBooksWithIncreasingOrderOfTheirPrice() {
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksWithIncreasingOrderOfTheirPrice();
        ResponseDTO responseDTO = new ResponseDTO("Showing books in ascending order",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getBookDescending")
    public ResponseEntity<ResponseDTO> getBooksWithDecreasingOrderOfTheirPrice() {
        List<BookDetailsModel> bookDetailsModels = bookService.getBooksWithDecreasingOrderOfTheirPrice();
        ResponseDTO responseDTO = new ResponseDTO("Showing books in descending order",
                bookDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}