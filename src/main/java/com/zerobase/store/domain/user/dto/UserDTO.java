package com.zerobase.store.domain.user.dto;

import com.zerobase.store.domain.user.entity.User;
import lombok.Data;

import java.util.Collections;
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

        public User toUserEntity(){
            return User.builder()
                        .name(this.name)
                        .phoneNum(this.phoneNum)
                        .userName(this.userName)
                        .password(this.password)
                        .roles(Collections.singletonList("USER"))
                        .build();

        }

        public User toPartnerEntity(){
            return User.builder()
                    .name(this.name)
                    .phoneNum(this.phoneNum)
                    .userName(this.userName)
                    .password(this.password)
                    .roles(Collections.singletonList("PARTNER"))
                    .build();

        }
    }
}
