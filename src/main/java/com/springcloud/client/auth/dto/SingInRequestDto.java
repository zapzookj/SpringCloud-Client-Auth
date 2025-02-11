package com.springcloud.client.auth.dto;

import lombok.Getter;

@Getter
public class SingInRequestDto {
    private String username;
    private String password;
}
