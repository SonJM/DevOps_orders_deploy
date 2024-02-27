package com.practice.ordersystem.domain.OrderItem.Controller;

import com.practice.ordersystem.domain.OrderItem.DTO.OrderItemListResDto;
import com.practice.ordersystem.domain.OrderItem.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/orderitems/{id}")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Object> orderitemsList(@PathVariable Long id){
        try{
            List<OrderItemListResDto> orderItemList = orderItemService.findAllById(id);
            return ResponseEntity.status(HttpStatus.OK).body(orderItemList);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
}
