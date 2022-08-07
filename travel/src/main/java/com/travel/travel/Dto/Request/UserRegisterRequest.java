package com.travel.travel.Dto.Request;

import com.travel.travel.Enum.Role;
import com.travel.travel.Enum.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRegisterRequest {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private UserType userType;
}
