package com.bridgelabz.BookStoreApp.Controller;

import com.bridgelabz.BookStoreApp.DTO.OrderDTO;
import com.bridgelabz.BookStoreApp.DTO.ResponseDTO;
import com.bridgelabz.BookStoreApp.Model.OrderDetailsModel;
import com.bridgelabz.BookStoreApp.Model.UserData;
import com.bridgelabz.BookStoreApp.Service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orderApi")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<ResponseDTO> placeOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDetailsModel orderDetailsModel = orderService.placeOrder(orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("placed order successfully", orderDetailsModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> GetAllOrders() {
        List<OrderDetailsModel> orderDTOList = orderService.getAllOrderDetails();
        ResponseDTO responseDTO = new ResponseDTO("The List of Orders Found", orderDTOList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<ResponseDTO> GetAllOrders(@PathVariable(value = "orderId") int orderId) {
        OrderDetailsModel orderDTOList = orderService.getAllOrderDetailsByID(orderId);
        ResponseDTO responseDTO = new ResponseDTO("The List of Orders Found", orderDTOList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<ResponseDTO> cancel(@RequestHeader(value = "Authorization") String auth, @PathVariable(value = "orderId") long orderId) {
        orderService.cancelOrder(auth, orderId);
        ResponseDTO responseDTO = new ResponseDTO("The Order data with " + orderId + " is deleted", orderId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}