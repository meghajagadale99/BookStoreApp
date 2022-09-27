package com.bridgelabz.BookStoreApp.Repository;

import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<BookDetailsModel, Long> {
    Optional<BookDetailsModel> findByBookName(String bookName);

    @Query(value = "select * from book_details_model b where b.book_name like %:keyword%", nativeQuery = true)
    List<BookDetailsModel> findByKeyWord(@Param("keyword") String keyword);
}