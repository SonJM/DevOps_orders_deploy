package com.practice.ordersystem.domain.Ordering.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDto {
        private Long itemId;
        private int count;
}

// 예시 1 : List<ItemId>, List<count>
/*
{
    "itemIds" : [1, 2], "counts" : [10, 20]
}
 */

// 예시 2
/*
"orderReqItemDtos": [
{"itemId" : 1, "count" :10},
{"itemId" : 2, "count" :20}
]
 */
