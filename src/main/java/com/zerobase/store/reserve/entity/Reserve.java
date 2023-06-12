package com.zerobase.store.reserve.entity;

import com.zerobase.store.admin.entity.Shop;
import com.zerobase.store.global.BaseEntity;
import com.zerobase.store.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "RESERVE")
public class Reserve extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime reservedTime;

    @Enumerated
    private ReserveStatus status;
}
