package com.travel.travel.Dto;

import com.travel.travel.Enum.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private UserType userType;
}
