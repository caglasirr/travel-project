package com.travel.travel.Controller;

import com.travel.travel.Dto.Request.UserRegisterRequest;
import com.travel.travel.Dto.Request.UserLoginRequest;
import com.travel.travel.Service.OrderService;
import com.travel.travel.Service.TripService;
import com.travel.travel.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/users")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        userService.register(userRegisterRequest);
        return new ResponseEntity<String>("You registered successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest){
        userService.login(userLoginRequest);
        return new ResponseEntity<String>("Welcome to the Travelapp!!!",HttpStatus.OK);
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}