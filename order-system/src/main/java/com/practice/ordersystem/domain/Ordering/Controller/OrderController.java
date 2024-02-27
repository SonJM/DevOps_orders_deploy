package com.practice.ordersystem.domain.Ordering.Controller;

import com.practice.ordersystem.domain.Ordering.DTO.OrderReqDto;
import com.practice.ordersystem.domain.Ordering.DTO.OrderResDto;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Service.OrderService;
import com.practice.ordersystem.domain.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public List<OrderResDto> orderList(){
        return orderService.findAll();
    }

    @PostMapping("/order/new")
    public ResponseEntity<ResponseDto> newOrder(@RequestBody List<OrderReqDto> orderReqDtos){
        Ordering ordering = orderService.create(orderReqDtos);
        return new ResponseEntity<>(
                new ResponseDto(HttpStatus.CREATED, "order succesfully created", ordering.getId())
                , HttpStatus.CREATED);
    }

    @DeleteMapping("/order/{id}/cancel")
    public ResponseEntity<ResponseDto> orderCancel(@PathVariable Long id){
        Ordering ordering = orderService.cancel(id);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "order successfully canceled", ordering.getId()), HttpStatus.OK);
    }
}
