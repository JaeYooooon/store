package com.zerobase.store.domain.user.dto;

import com.zerobase.store.domain.user.entity.User;
import lombok.*;

import java.util.List;


public class UserDTO {
    @Data
    public static class SignIn{
        private String userName;
        private String password;
    }

    @Data
    public static class Delete{
        private String userName;
        private String password;
    }
    @Data
    public static class SignUp{
        private String name;
        private String phoneNum;
        private String userName;
        private String password;
        private List<String> roles;

        public User toEntity(){
            return User.builder()
                        .name(this.name)
                        .phoneNum(this.phoneNum)
                        .userName(this.userName)
                        .password(this.password)
                        .roles(this.roles)
                        .build();

        }
    }
    @Data
    public static class LogoutRequest {
        private String token;

        public LogoutRequest(String token) {
            this.token = token;
        }
    }
}
