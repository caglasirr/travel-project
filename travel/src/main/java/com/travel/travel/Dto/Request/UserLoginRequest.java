package com.travel.travel.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserLoginRequest {
    private String email;
    private String password;
}