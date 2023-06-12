package com.zerobase.store.domain.user.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDTO {
    private String name;
    private String phoneNum;
    private String userName;
    private String password;
}
