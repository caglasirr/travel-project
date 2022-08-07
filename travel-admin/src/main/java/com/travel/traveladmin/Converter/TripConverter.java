package com.travel.traveladmin.Converter;

import com.travel.traveladmin.Dto.TripRequest;
import com.travel.traveladmin.Enum.TripStatus;
import com.travel.traveladmin.Model.Trip;
import org.springframework.stereotype.Service;

@Service
public class TripConverter {

    public Trip convert(TripRequest request){
        return Trip.builder().fromCity(request.getFromCity())
                .toCity(request.getToCity())
                .date(request.getDate())
                .vehicleType(request.getVehicleType()).build();
    }

    public TripRequest convert(Trip trip){
        return TripRequest.builder().fromCity(trip.getFromCity())
                .toCity(trip.getToCity())
                .date(trip.getDate())
                .vehicleType(trip.getVehicleType()).build();

    }
}
