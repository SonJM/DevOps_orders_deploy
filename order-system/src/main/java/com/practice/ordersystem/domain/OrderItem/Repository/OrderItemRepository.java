package com.practice.ordersystem.domain.OrderItem.Repository;

import com.practice.ordersystem.domain.OrderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    public List<OrderItem> findAllByOrdering(Long id);
}
