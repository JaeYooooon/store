package com.zerobase.store.domain.review.entity;

import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.global.entity.BaseEntity;
import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.global.exception.CustomException;
import com.zerobase.store.global.exception.ErrorCode;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "REVIEW")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String content;

    private int star;

}
