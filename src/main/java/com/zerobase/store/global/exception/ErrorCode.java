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
    NONE_CORRECT_PW(HttpStatus.BAD_REQUEST, "패스워드가 틀렸습니다. ")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
