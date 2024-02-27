package com.practice.ordersystem.domain.Member.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderListResDto {
    private String name;
    private Long orderId;
    private String orderStatus;
}
