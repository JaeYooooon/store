package com.zerobase.store.domain.user.controller;

import com.zerobase.store.domain.user.dto.UserDTO;
import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.domain.user.repository.UserRepository;
import com.zerobase.store.domain.user.service.UserService;
import com.zerobase.store.global.security.TokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "회원 API")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "유저 회원가입", notes = "이름, 번호, 아이디, 비밀번호 입력 후 회원가입")
    @PostMapping("/signup/user")
    public ResponseEntity<?> userRegister(@RequestBody UserDTO.SignUp request) {

        User user = userService.userRegister(request);

        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "파트너 회원가입", notes = "이름, 번호, 아이디, 비밀번호 입력 후 회원가입")
    @PostMapping("/signup/partner")
    public ResponseEntity<?> partnerRegister(@RequestBody UserDTO.SignUp request) {

        User user = userService.partnerRegister(request);

        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "로그인", notes = "아이디, 비밀번호 후 로그인")
    @PostMapping("/signin")
    public ResponseEntity<?> register(@RequestBody UserDTO.SignIn request) {
        User user = this.userService.authenticate(request);
        String token = this.tokenProvider.createToken(user.getUsername(), user.getRoles());
        log.info("login -> " + request.getUserName());

        return ResponseEntity.ok("Bearer " + token);
    }

    @ApiOperation(value = "회원탈퇴", notes = "아이디, 비밀번호 입력 후 회원탈퇴")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody UserDTO.Delete request){
        userService.delete(request.getUserName(), request.getPassword());

        return ResponseEntity.ok("회원탈퇴 완료");
    }
}
