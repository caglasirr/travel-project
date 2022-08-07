package com.travel.travel.Converter;

import com.travel.travel.Dto.Request.UserRegisterRequest;
import com.travel.travel.Dto.UserDto;
import com.travel.travel.Enum.Role;
import com.travel.travel.Model.User;
import com.travel.travel.Security.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConvertor {

    @Autowired
    private Encryptor encryptor;

    public UserDto convert(User user){
        return UserDto.builder().name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType())
                .build();
    }

    public User convert(UserRegisterRequest request){
        return User.builder().name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(encryptor.encryptGivenPassword(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .userType(request.getUserType())
                .role(Role.USER).build();
    }

}
