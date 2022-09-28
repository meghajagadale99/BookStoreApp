package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.DTO.OrderDTO;
import com.bridgelabz.BookStoreApp.Model.OrderDetailsModel;

import java.util.List;

public interface IOrderService {
    OrderDetailsModel placeOrder(OrderDTO orderDTO);

    List<OrderDetailsModel> getAllOrderDetails();

    OrderDetailsModel getAllOrderDetailsByID(long orderId);

    List<OrderDetailsModel> cancelOrder(String auth, long orderId);

}