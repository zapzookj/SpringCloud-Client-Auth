package com.springcloud.client.auth;

import com.springcloud.client.auth.dto.SignUpRequestDto;
import com.springcloud.client.auth.dto.SingInRequestDto;
import com.springcloud.client.auth.entity.User;
import com.springcloud.client.auth.entity.UserRole;
import com.springcloud.client.auth.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRole role = requestDto.getRole();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("중복된 사용자명이 존재합니다.");
        }

        userRepository.save(new User(username, password, role));
    }

    public String signIn(SingInRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() ->
            new IllegalArgumentException("잘못된 사용자명 또는 비밀번호입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 사용자명 또는 비밀번호입니다.");
        }

        return jwtUtils.createToken(username, user.getRole());
    }
}
