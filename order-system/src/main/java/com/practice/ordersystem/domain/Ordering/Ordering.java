package com.practice.ordersystem.domain.Ordering;

import com.practice.ordersystem.domain.OrderItem.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.practice.ordersystem.domain.Member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// @Builder, @AllArgsConstructor 사용 시 초기값을 설정해놔도 ex) orderStatus = OrderStatus.ORDERED; ==> Hello 객체
// @Builder.Default로 값을 넣어주지 않으면 null이 들어감 ==> myBuilder 객체에 null이 담김
// 이 클래스에서는 직접 생성자를 만드는 방식으로 구현
// id, orderStatus, orderItems에는 초기 값으로 설정,
// member만 생성해주면 -> 나머지 초기 값들 유지
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.ORDERED;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    @Builder
    Ordering(Member member){

    }

    public void setMember(Member member) {
        this.member = member;
        // 무한루프에 빠지지 않도록 체크
        if(!member.getOrders().contains(this)) {
            member.getOrders().add(this);
        }
    }
    public void changeStatus(){
        if(this.status == OrderStatus.ORDERED)
            this.status = OrderStatus.CANCELED;
    }
}
