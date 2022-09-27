package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import com.bridgelabz.BookStoreApp.Model.CartDetailsModel;

import java.util.List;

public interface ICartService {

    List<CartDetailsModel> getAll();

    CartDetailsModel getCartItemById(Long cartId);

    BookDetailsModel addBookToCart(String auth, Long bookId);

    CartDetailsModel updateCartQuantityById(Long cartId, Double quantity);

    CartDetailsModel deleteCartItemById(Long cartId);
}