package com.practice.ordersystem.domain.Ordering.Service;

import com.practice.ordersystem.domain.Item.Item;
import com.practice.ordersystem.domain.Item.Repository.ItemRepository;
import com.practice.ordersystem.domain.Member.Member;
import com.practice.ordersystem.domain.Member.Repository.MemberRepository;
import com.practice.ordersystem.domain.OrderItem.OrderItem;
import com.practice.ordersystem.domain.OrderItem.Repository.OrderItemRepository;
import com.practice.ordersystem.domain.Ordering.DTO.OrderReqDto;
import com.practice.ordersystem.domain.Ordering.DTO.OrderResDto;
import com.practice.ordersystem.domain.Ordering.OrderStatus;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public List<OrderResDto> findAll() {
        List<Ordering> orderings = orderRepository.findAll();
        return orderings.stream().map(o -> OrderResDto.toDto(o)).collect(Collectors.toList());
    }

    public Ordering create(List<OrderReqDto> orderReqDtos) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("not found email"));

        // id, orderStatus, orderItems에는 Entity에서 정의한 초기 값으로 설정, member만 넣어줌
        Ordering ordering = Ordering.builder().member(member).build();

        // Ordeing 객체가 생성될 때 OrderingItem 객체도 함께 생성 : Cascading
        // Cascading PERSIST
        for(OrderReqDto dto : orderReqDtos){
            Item item = itemRepository.findById(dto.getItemId()).orElseThrow(()->new EntityNotFoundException("not found item"));

            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .quantity(dto.getCount())
                    .ordering(ordering)
                    .build();

            // 빈 List<OrderItem>에 orderItem들을 삽입 -> orderItem DB에는 자동으로 cascading되어 추가됨
            ordering.getOrderItemList().add(orderItem);

            int newStockQuantity = item.getStockQuantity()-dto.getCount();
            if(newStockQuantity < 0){
                throw new IllegalArgumentException("not enough stock");
            }
            orderItem.getItem().updateItem(newStockQuantity); //dirty checking
        }
        return orderRepository.save(ordering);
    }

    public Ordering cancel(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        //order 구한 뒤 order member의 email과 비교
        Ordering ordering = orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found order"));

        //자기 자신, 관리자만 수행 가능
        if(!ordering.getMember().getEmail().equals(email) && !authentication.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            throw new AccessDeniedException("access denied");
            // 프로젝트 때는 ExceptionHandler로 잡아 403에러 발생시키기
        }

        // 이미 취소된 주문에 대해서는 취소 불가능
        if(ordering.getStatus() == OrderStatus.CANCELED){
            throw new IllegalArgumentException("already canceled order");
        }

        ordering.changeStatus();
        for(OrderItem orderItem : ordering.getOrderItemList()){
            int newStockQuantity = orderItem.getItem().getStockQuantity() + orderItem.getQuantity();
            orderItem.getItem().updateItem(newStockQuantity); //dirty checking
        }
        return orderRepository.save(ordering);
    }

    public List<OrderResDto> findByMember(Long id) {
//        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("member not found"));
//        List<Ordering> orderings = member.getOrderings();

        List<Ordering> orderings = orderRepository.findAllByMemberId(id);
        return orderings.stream().map(o -> OrderResDto.toDto(o)).collect(Collectors.toList());
    }

    public List<OrderResDto> findMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("member not found"));

        List<Ordering> orderings = member.getOrders();
        return orderings.stream().map(o -> OrderResDto.toDto(o)).collect(Collectors.toList());
    }
}
