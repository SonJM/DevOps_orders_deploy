package com.practice.ordersystem.domain.OrderItem.Service;

import com.practice.ordersystem.domain.OrderItem.DTO.OrderItemListResDto;
import com.practice.ordersystem.domain.OrderItem.OrderItem;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class OrderItemService {
    private final OrderRepository orderRepository;


    @Autowired
    public OrderItemService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderItemListResDto> findAllById(Long id) throws EntityNotFoundException{
        Ordering ordering = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<OrderItem> orderItemList = ordering.getOrderItemList();
        List<OrderItemListResDto> orderItemListResDtos = new ArrayList<>();
        for(OrderItem orderItem : orderItemList){
            OrderItemListResDto orderItemListResDto = OrderItemListResDto.builder()
                    .itemName(orderItem.getItem().getName())
                    .orderId(orderItem.getOrdering().getId())
                    .quantity(orderItem.getQuantity())
                    .build();
            orderItemListResDtos.add(orderItemListResDto);
        }
        return orderItemListResDtos;
    }
}
