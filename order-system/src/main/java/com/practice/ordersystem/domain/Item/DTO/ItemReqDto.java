package com.practice.ordersystem.domain.Item.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class ItemReqDto {
    private String name;
    private String category;
    private int price;
    private int stockQuantity;
    private MultipartFile itemImage;
}
