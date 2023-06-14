package com.zerobase.store.domain.reserve.entity;

import com.zerobase.store.domain.reserve.entity.status.CheckStatus;
import com.zerobase.store.domain.reserve.entity.status.ReserveStatus;
import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.global.entity.BaseEntity;
import com.zerobase.store.domain.user.entity.User;
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

    @Enumerated(EnumType.STRING)
    private ReserveStatus status = ReserveStatus.DEFAULT;

    @Enumerated(EnumType.STRING)
    private CheckStatus checkStatus = CheckStatus.DEFAULT;
}
