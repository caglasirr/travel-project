package com.travel.travel.Controller;

import com.travel.travel.Dto.OrderDto;
import com.travel.travel.Dto.Request.UserRegisterRequest;
import com.travel.travel.Dto.Request.UserLoginRequest;
import com.travel.travel.Dto.Request.TicketRequest;
import com.travel.travel.Dto.TripDto;
import com.travel.travel.Enum.VehicleType;
import com.travel.travel.Service.OrderService;
import com.travel.travel.Service.TripService;
import com.travel.travel.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TripService tripService;
    @Autowired
    private OrderService orderService;

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

    @GetMapping(value = "/trip/{fromCity}/{toCity}")
    public ResponseEntity<List<TripDto>> searchTripByCity(@PathVariable String fromCity, @PathVariable String toCity){
        List list = tripService.searchTripByCity(fromCity, toCity);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/trip/date/{date}")
    public ResponseEntity<List<TripDto>> searchTripByDate(@PathVariable String date){
        List list = tripService.searchTripByDate(date);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/trip/{vehicleType}")
    public ResponseEntity<List<TripDto>> searchTripByVehicleType(@PathVariable VehicleType vehicleType){
        List list = tripService.searchTripByVehicleType(vehicleType);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/ticket")
    public ResponseEntity<String> createOrder(@PathVariable int userId, @RequestBody TicketRequest ticketRequest){
        orderService.createOrder(userId,ticketRequest);
        return new ResponseEntity<>("You have successfully purchased your tickets!", HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/tickets")
    public ResponseEntity<List<OrderDto>> findTickets(@PathVariable int userId){
        List list = orderService.findTickets(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}