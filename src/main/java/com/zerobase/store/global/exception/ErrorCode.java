package com.zerobase.store.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // 유저
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 등록 되어있는 사용자입니다. "),
    NONE_EXIST_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 ID 입니다. "),
    NONE_CORRECT_PW(HttpStatus.BAD_REQUEST, "패스워드가 틀렸습니다. "),
    NO_PERMISSION(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    NOT_FOUND_SHOP(HttpStatus.NOT_FOUND, "상점을 찾을 수 없습니다."),
    ALREADY_EXIST_SHOP(HttpStatus.BAD_REQUEST, "이미 등록 되어있는 상점명 입니다. "),

    NOT_FOUND_RESERVE(HttpStatus.NOT_FOUND, "예약내역을 찾을 수 없습니다."),
    CHECK_RESERVE(HttpStatus.NOT_FOUND, "예약내역을 확인해주세요. "),
    INVALID_RESERVE_TIME(HttpStatus.BAD_REQUEST, "예약할 수 없는 시간입니다. "),

    EARLY_CHECK_IN(HttpStatus.BAD_REQUEST, "예약시간 10분전 체크인을 해주세요. "),
    LATE_CHECK_IN(HttpStatus.BAD_REQUEST, "예약시간이 지났습니다. ");

    private final HttpStatus httpStatus;
    private final String detail;
}
