package com.practice.ordersystem.domain.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.practice.ordersystem.domain.Item.Item;

import javax.persistence.*;

import com.practice.ordersystem.domain.Ordering.Ordering;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @JoinColumn(name = "ordering_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Ordering ordering;

    @JoinColumn(name = "item_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    public void setOrdering(Ordering ordering) {
        this.ordering = ordering;
        // 무한루프에 빠지지 않도록 체크
        if(!ordering.getOrderItemList().contains(this)) {
            ordering.getOrderItemList().add(this);
        }
    }

    public void setItem(Item item) {
        this.item = item;
        // 무한루프에 빠지지 않도록 체크
        if(!item.getOrderItemList().contains(this)) {
            ordering.getOrderItemList().add(this);
        }
    }
}
