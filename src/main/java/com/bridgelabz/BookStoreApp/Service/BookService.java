package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.DTO.BookDTO;
import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import com.bridgelabz.BookStoreApp.Model.UserData;
import com.bridgelabz.BookStoreApp.Exception.BookStoreException;
import com.bridgelabz.BookStoreApp.Exception.UserException;
import com.bridgelabz.BookStoreApp.Repository.IBookRepository;
import com.bridgelabz.BookStoreApp.Repository.UserRegistrationRepository;
import com.bridgelabz.BookStoreApp.Util.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private BookDetailsModel bookDetailsModel;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Override
    public List<BookDetailsModel> getAllBooks() {
        if (bookRepository.findAll().size() == 0) {
            throw new BookStoreException(BookStoreException.ExceptionTypes.NO_BOOKS_FOUND);
        }
        return bookRepository.findAll();
    }

    @Override
    public BookDetailsModel getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
    }

    @Override
    public BookDetailsModel addBook(BookDTO bookDTO, String auth) {
        Long id = Long.valueOf(tokenGenerator.decodeJWT(auth));
        System.out.println("the user id is " + id);
        UserData userData = userRegistrationRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found", UserException.ExceptionType.USER_NOT_FOUND));
        String userRole = userData.getRole();
        System.out.println(userData.getEmail());
        if (userRole.equals("seller")) {
            Optional<BookDetailsModel> searchByName = bookRepository.findByBookName(bookDTO.getBookName());
            if (searchByName.isPresent()) {
                throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_AlREADY_PRESENT);
            }

            bookDetailsModel = new BookDetailsModel(bookDTO);
            bookDetailsModel.setUserId(id);
            return bookRepository.save(bookDetailsModel);
        }
        throw new UserException("you are not a Authorised user", UserException.ExceptionType.USER_UNAUTHORISED);
    }

    @Override
    public String deleteBookById(Long bookId) {
        BookDetailsModel bookById = getBookById(bookId);


        UserData userData = userRegistrationRepository.findById(bookById.getUserId()).get();
        System.out.println("role is " + userData.getRole());
        if (userData.getRole().equals("seller")) {
            System.out.println("inside if statement");
            bookRepository.delete(bookById);
            return bookById.getBookName();
        }
        throw new UserException("You are not authorised to delete this book", UserException.ExceptionType.USER_UNAUTHORISED);
    }

    @Override
    public BookDetailsModel updateBookById(Long bookId, String auth, BookDTO bookDTO) {
        Long userId = Long.valueOf(tokenGenerator.decodeJWT(auth));
        UserData userData = userRegistrationService.findUserById(userId);
        if (!(userData == null)) {
            System.out.println(userData.getRole());
            if (userData.getRole().equals("seller")) {
                BookDetailsModel bookDetailsModel = getBookById(bookId);
                bookDetailsModel.updateBookDetails(bookDTO);
                bookRepository.save(bookDetailsModel);
                return bookDetailsModel;
            } else {
                throw new UserException("The user is un authorised to update the book", UserException.ExceptionType.USER_UNAUTHORISED);
            }
        } else {
            throw new UserException("The user not found contact admin", UserException.ExceptionType.USER_NOT_FOUND);
        }

    }

    @Override
    public List<BookDetailsModel> searchByName(String name) {
        String name1 = name.toLowerCase();
//      List<BookDetailsModel> bookDetailsModels = bookRepository.findByKeyWord(name);
        List<BookDetailsModel> bookDetailsModels = getAllBooks();
        List<BookDetailsModel> collect = bookDetailsModels
                .stream()
                .filter(bookDetailsModel -> bookDetailsModel.getBookName().toLowerCase().contains(name1))
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public int getCountOfBooks() {
        return bookRepository.findAll().size();
    }

    @Override
    public List<BookDetailsModel> getBooksWithPublishingYear() {
        List<BookDetailsModel> bookDetails = bookRepository.findAll()
                .stream().sorted(Comparator.comparing(bookDetailsModel -> bookDetailsModel.getPublishingYear()))
                .collect(Collectors.toList());
        Collections.reverse(bookDetails);
        return bookDetails;
    }

    @Override
    public List<BookDetailsModel> getBooksByNewLaunch() {
        List<BookDetailsModel> bookDetails = bookRepository.findAll()
                .stream().sorted(Comparator.comparing(bookDetailsModel -> bookDetailsModel.getCreatedAt()))
                .collect(Collectors.toList());
        return bookDetails;
    }

    @Override
    public List<BookDetailsModel> getBooksWithIncreasingOrderOfTheirPrice() {
        List<BookDetailsModel> bookDetails = bookRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(bookDetailsModel -> bookDetailsModel.getBookPrice()))
                .collect(Collectors.toList());
        return bookDetails;
    }

    @Override
    public List<BookDetailsModel> getBooksWithDecreasingOrderOfTheirPrice() {
        List<BookDetailsModel> bookDetails = bookRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(bookDetailsModel -> bookDetailsModel.getBookPrice()))
                .collect(Collectors.toList());
        Collections.reverse(bookDetails);
        return bookDetails;
    }
}