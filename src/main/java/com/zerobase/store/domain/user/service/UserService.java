package com.zerobase.store.domain.user.service;

import com.zerobase.store.domain.user.dto.UserDTO;
import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.domain.user.repository.UserRepository;
import com.zerobase.store.global.exception.CustomException;
import com.zerobase.store.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.store.global.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUserName(username)
                .orElseThrow();
    }

    // 유저 로그인
    @Transactional
    public User userRegister(UserDTO.SignUp signUp){
        boolean exist = this.userRepository.existsByUserName(signUp.getUserName());
        if(exist){
            throw new CustomException(ALREADY_EXIST_USER);
        }
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));

        return this.userRepository.save(signUp.toUserEntity());
    }


    // 관리자 로그인
    @Transactional
    public User partnerRegister(UserDTO.SignUp signUp){
        boolean exist = this.userRepository.existsByUserName(signUp.getUserName());
        if(exist){
            throw new CustomException(ALREADY_EXIST_USER);
        }
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));

        return this.userRepository.save(signUp.toPartnerEntity());
    }

    public User authenticate(UserDTO.SignIn signIn){
        User user = this.userRepository.findByUserName(signIn.getUserName())
                .orElseThrow(() -> new CustomException(NONE_EXIST_ID));

        if(!this.passwordEncoder.matches(signIn.getPassword(), user.getPassword())){
            throw new CustomException(NONE_CORRECT_PW);
        }

        return user;
    }

    public void delete(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(NONE_EXIST_ID));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(NONE_CORRECT_PW);
        }

        userRepository.delete(user);
    }
}
