package com.practice.ordersystem.domain.Item.Repository;

import com.practice.ordersystem.domain.Item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long id);
    Page<Item> findAll(Specification<Item> specification, Pageable pageable);

    // Page<Item> findAllByDelYnAndCategoryLikeOrNameLike(String delYn, String category, String name, Pageable pageable);
}
