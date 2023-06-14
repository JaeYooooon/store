package com.zerobase.store.domain.reserve.dto;

import com.zerobase.store.domain.reserve.entity.status.CheckStatus;
import com.zerobase.store.domain.reserve.entity.status.ReserveStatus;
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
    private CheckStatus checkStatus;
}
