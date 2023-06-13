package com.zerobase.store.domain.user.controller;

import com.zerobase.store.domain.user.dto.UserDTO;
import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.domain.user.repository.UserRepository;
import com.zerobase.store.domain.user.service.UserService;
import com.zerobase.store.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @PostMapping("/user/signup")
    public ResponseEntity<?> userRegister(@RequestBody UserDTO.SignUp request) {

        User user = userService.userRegister(request);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/partner/signup")
    public ResponseEntity<?> partnerRegister(@RequestBody UserDTO.SignUp request) {

        User user = userService.partnerRegister(request);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> register(@RequestBody UserDTO.SignIn request) {
        User user = this.userService.authenticate(request);
        String token = this.tokenProvider.createToken(user.getUsername(), user.getRoles());
        log.info("login -> " + request.getUserName());

        return ResponseEntity.ok("Bearer " + token);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody UserDTO.Delete request){
        userService.delete(request.getUserName(), request.getPassword());

        return ResponseEntity.ok("회원탈퇴 완료");
    }
}
