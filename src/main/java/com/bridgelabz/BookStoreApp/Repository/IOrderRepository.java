package com.bridgelabz.BookStoreApp.Repository;

import com.bridgelabz.BookStoreApp.Model.OrderDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderDetailsModel, Long> {
}