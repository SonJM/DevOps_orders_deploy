package com.practice.ordersystem.domain.Ordering.DTO;

import lombok.Data;
import com.practice.ordersystem.domain.OrderItem.OrderItem;
import com.practice.ordersystem.domain.Ordering.Ordering;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResDto {
    private Long id;
    private String memberEmail;
    private String orderStatus;
    private List<OrderResDto.OrderResItemDto> orderItems;
    @Data
    public static class OrderResItemDto{
        private Long id;
        private String itemName;
        private int count;
    }

    //공통 코드 -> 겹칠 것 같아서 ; ordering->orderResDto
    public static OrderResDto toDto(Ordering ordering){
        OrderResDto orderResDto= new OrderResDto();
        orderResDto.setId(ordering.getId());
        orderResDto.setMemberEmail(ordering.getMember().getEmail());
        orderResDto.setOrderStatus(ordering.getStatus().toString());    //enum 객체라서 toString()
        List<OrderResDto.OrderResItemDto> orderResItemDtos = new ArrayList<>();
        for(OrderItem orderItem: ordering.getOrderItemList()){
            OrderResDto.OrderResItemDto dto= new OrderResItemDto();
            dto.setId(orderItem.getId());
            dto.setItemName(orderItem.getItem().getName());
            dto.setCount(orderItem.getQuantity());
            orderResItemDtos.add(dto);
        }
        orderResDto.setOrderItems(orderResItemDtos);
        return orderResDto;
    }
}