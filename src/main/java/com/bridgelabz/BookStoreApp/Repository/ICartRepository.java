package com.bridgelabz.BookStoreApp.Repository;

import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import com.bridgelabz.BookStoreApp.Model.CartDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<CartDetailsModel, Long> {

    @Query(value = "select * from cart_details_model where book_id=:bookId", nativeQuery = true)
    Optional<CartDetailsModel> findByBookDetailsById(Long bookId);
}