package com.travel.travel.Converter;

import com.travel.travel.Dto.PassengerDto;
import com.travel.travel.Model.Passenger;
import org.springframework.stereotype.Service;

@Service
public class PassengerConverter {
    public PassengerDto convert(Passenger passenger){
        return PassengerDto.builder().name(passenger.getName())
                .surname(passenger.getSurname())
                .sex(passenger.getSex()).build();
    }
}
