package com.zerobase.store.domain.shop.repository;

import com.zerobase.store.domain.shop.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    boolean existsByName(String name);
    Page<Shop> findAllByOrderByNameAsc(Pageable pageable);
    Page<Shop> findAllByOrderByStarAvgDesc(Pageable pageable);
    Page<Shop> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
