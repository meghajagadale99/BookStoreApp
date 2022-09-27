package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import com.bridgelabz.BookStoreApp.Model.CartDetailsModel;
import com.bridgelabz.BookStoreApp.Model.UserData;
import com.bridgelabz.BookStoreApp.Exception.CartException;
import com.bridgelabz.BookStoreApp.Repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private BookDetailsModel bookDetailsModel;

    @Autowired
    private UserData userData;

    @Autowired
    private IBookService bookService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private CartDetailsModel cartDetailsModel;

    @Autowired
    private ICartRepository cartRepository;

    @Override
    public List<CartDetailsModel> getAll() {
        List<CartDetailsModel> cartDetailsModelList = cartRepository.findAll();
        return cartDetailsModelList;
    }

    @Override
    public CartDetailsModel getCartItemById(Long cartId) {
        Optional<CartDetailsModel> cartItemById = cartRepository.findById(cartId);
        if (cartItemById.isPresent()) {
            return cartItemById.get();
        }
        throw new CartException(CartException.ExceptionTypes.CART_ITEM_NOT_FOUND);

    }

    @Override
    public BookDetailsModel addBookToCart(String auth, Long bookId) {
        Optional<CartDetailsModel> cartModel = cartRepository.findByBookDetailsById(bookId);
        if (cartModel.isPresent()) {
            throw new CartException(CartException.ExceptionTypes.BOOK_ALREADY_PRESENT);
        }
        bookDetailsModel = bookService.getBookById(bookId);
        userData = userRegistrationService.findUserById(bookDetailsModel.getUserId());
        CartDetailsModel detailsModel = new CartDetailsModel();
        detailsModel.setBookDetailsModel(bookDetailsModel);
        detailsModel.setUserData(userData);
        detailsModel.setQuantity(1.0);
        cartRepository.save(detailsModel);
        return bookDetailsModel;
    }

    @Override
    public CartDetailsModel updateCartQuantityById(Long cartId, Double quantity) {
        Optional<CartDetailsModel> detailsModel = cartRepository.findById(cartId);
        if (detailsModel.isEmpty()) {
            throw new CartException(CartException.ExceptionTypes.CART_ITEM_NOT_FOUND);
        }
        cartDetailsModel = detailsModel.get();
        cartDetailsModel.setQuantity(quantity);
        cartRepository.save(cartDetailsModel);
        return cartDetailsModel;
    }

    @Override
    public CartDetailsModel deleteCartItemById(Long cartId) {
        cartDetailsModel = getCartItemById(cartId);
        cartRepository.delete(cartDetailsModel);
        return cartDetailsModel;
    }

}