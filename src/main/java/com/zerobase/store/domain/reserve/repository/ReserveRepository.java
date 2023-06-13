package com.zerobase.store.domain.reserve.repository;

import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zerobase.store.domain.reserve.entity.Reserve;

import java.util.List;


@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findByUser(User user);
    List<Reserve> findByShop(Shop shop);
}
