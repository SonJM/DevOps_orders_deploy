package com.practice.ordersystem.domain.Ordering.Repository;

import com.practice.ordersystem.domain.Ordering.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Long> {
    List<Ordering> findAllById(Long id);

    List<Ordering> findAllByMemberId(Long id);
}
