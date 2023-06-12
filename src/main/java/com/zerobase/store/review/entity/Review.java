package com.zerobase.store.review.entity;

import com.zerobase.store.admin.entity.Shop;
import com.zerobase.store.global.BaseEntity;
import com.zerobase.store.user.entity.User;
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
