package com.zerobase.store.domain.reserve.dto;

import com.zerobase.store.domain.reserve.entity.Reserve;
import com.zerobase.store.domain.reserve.entity.ReserveStatus;
import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveDTO {
    private Long id;
    private Long shopId;
    private String shopName;
    private Long userId;
    private LocalDateTime reservedTime;
    private ReserveStatus status;
}
