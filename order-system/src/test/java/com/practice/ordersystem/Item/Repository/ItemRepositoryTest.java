package com.practice.ordersystem.Item.Repository;

import com.practice.ordersystem.domain.Item.Item;
import com.practice.ordersystem.domain.Item.Repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void add() {
        Item item = Item.builder()
                .name("사과")
                .price(50)
                .stockQuantity(50)
                .build();
        itemRepository.save(item);

        item = Item.builder()
                .name("배")
                .price(40)
                .stockQuantity(50)
                .build();
        itemRepository.save(item);

        item = Item.builder()
                .name("딸기")
                .price(50)
                .stockQuantity(50)
                .build();
        itemRepository.save(item);
    }
}
