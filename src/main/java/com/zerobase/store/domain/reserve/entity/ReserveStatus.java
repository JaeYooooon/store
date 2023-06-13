package com.zerobase.store.domain.reserve.entity;

public enum ReserveStatus {
    PENDING, // 대기중
    APPROVED, // 승인됨
    REJECTED; // 거절됨

    public static final ReserveStatus DEFAULT = PENDING;
}
