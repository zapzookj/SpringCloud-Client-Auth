package com.springcloud.client.auth;

import com.springcloud.client.auth.dto.SignUpRequestDto;
import com.springcloud.client.auth.dto.SingInRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        authService.signUp(requestDto);
        return ResponseEntity.ok().body("회원가입 완료");
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SingInRequestDto requestDto) {
        return ResponseEntity.ok(new AuthResponse(authService.signIn(requestDto)));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String accessToken;
    }
}
