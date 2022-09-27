package com.bridgelabz.BookStoreApp.Controller;

import com.bridgelabz.BookStoreApp.DTO.ResponseDTO;
import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import com.bridgelabz.BookStoreApp.Model.CartDetailsModel;
import com.bridgelabz.BookStoreApp.Service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cartApi")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/welcome")
    public String welcomeToCart() {
        return "Welcome to cart controller";
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        List<CartDetailsModel> cartDetailsModels = cartService.getAll();
        ResponseDTO responseDTO = new ResponseDTO("Cart details getting successfully", cartDetailsModels);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{cartId}")
    public ResponseEntity<ResponseDTO> getCartItemById(@PathVariable Long cartId) {
        CartDetailsModel cartDetailsModel = cartService.getCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("cart item found successfully", cartDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/addBookToCart/{bookId}")
    public ResponseEntity<ResponseDTO> addBookToCart(@RequestHeader(value = "Authorization") String auth , @PathVariable Long bookId) {
        BookDetailsModel bookDetailsModel = cartService.addBookToCart(auth, bookId);
        ResponseDTO responseDTO = new ResponseDTO("Book Added Successfully", bookDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/updateQuantity/{cartId}/{quantity}")
    public ResponseEntity<ResponseDTO> updateCartQuantityById(@PathVariable Long cartId, @PathVariable Double quantity) {
        CartDetailsModel cartDetailsModel = cartService.updateCartQuantityById(cartId, quantity);
        ResponseDTO responseDTO = new ResponseDTO("cart quantity updated successfully", cartDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartItemById(@PathVariable Long cartId) {
        CartDetailsModel cartDetailsModel = cartService.deleteCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Item Deleted successfully", cartDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}