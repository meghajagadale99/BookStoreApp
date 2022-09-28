package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.DTO.OrderDTO;
import com.bridgelabz.BookStoreApp.Model.BookDetailsModel;
import com.bridgelabz.BookStoreApp.Model.OrderDetailsModel;
import com.bridgelabz.BookStoreApp.Model.UserData;
import com.bridgelabz.BookStoreApp.Exception.BookStoreException;
import com.bridgelabz.BookStoreApp.Exception.OrderException;
import com.bridgelabz.BookStoreApp.Repository.IBookRepository;
import com.bridgelabz.BookStoreApp.Repository.IOrderRepository;
import com.bridgelabz.BookStoreApp.Repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService implements IOrderService {
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private UserRegistrationRepository userRepository;
    @Autowired
    private IOrderRepository orderRepository;


    @Override
    public OrderDetailsModel placeOrder(OrderDTO orderDTO) {
        log.info(orderDTO.toString());
        Optional<BookDetailsModel> book = bookRepository.findById(orderDTO.getBookId());

        Optional<UserData> user = userRepository.findById(orderDTO.getUserId());
        if (book.isPresent() && user.isPresent()) {
            if (orderDTO.getQuantity() <= book.get().getQuantity()) {
                OrderDetailsModel order =
                        new OrderDetailsModel(
                                orderDTO.getPrice(),
                                orderDTO.getQuantity(),
                                orderDTO.getAddress(),
                                user.get(),
                                book.get(),
                                orderDTO.getCancel()
                        );
                orderRepository.save(order);
                log.info("order record inserted successfully");
                return order;
            } else {
                throw new OrderException(OrderException.ExceptionTypes.BOOK_QUANTITY_EXCEEDED);
            }
        }
        throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_OR_USER_DOES_NOT_EXIST);
    }

    @Override
    public List<OrderDetailsModel> getAllOrderDetails() {
        return orderRepository.findAll();
    }

    @Override
    public OrderDetailsModel getAllOrderDetailsByID(long orderId) {
        return orderRepository.findById(orderId).get();
    }
    @Override
    public List<OrderDetailsModel> cancelOrder(String auth, long orderId) {
        orderRepository.deleteById(orderId);
        return orderRepository.findAll();
    }


}