package com.travel.travel.Converter;

import com.travel.travel.Dto.TripDto;
import com.travel.travel.Model.Trip;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripConverter {

    public TripDto convert(Trip trip){
        return TripDto.builder().fromCity(trip.getFromCity())
                .toCity(trip.getToCity())
                .date(trip.getDate())
                .vehicleType(trip.getVehicleType()).build();
    }

    public List<TripDto> convert(List<Trip> trips){
        List<TripDto> tripDtoList = new ArrayList<>();
        trips.forEach(trip -> tripDtoList.add(convert(trip)));
        return tripDtoList;
    }
}
