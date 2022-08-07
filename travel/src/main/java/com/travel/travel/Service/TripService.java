package com.travel.travel.Service;

import com.travel.travel.Converter.TripConverter;
import com.travel.travel.Dto.TripDto;
import com.travel.travel.Enum.VehicleType;
import com.travel.travel.Exception.TravelException;
import com.travel.travel.Model.Trip;
import com.travel.travel.Repository.TripRepository;
import com.travel.travel.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private TripRepository tripRepository;
    private UserRepository userRepository;
    private TripConverter tripConverter;

    public List<TripDto> searchTripByCity(String fromCity, String toCity){
        List<Trip> list = tripRepository.findByFromCityAndToCity(fromCity, toCity).
                orElseThrow(()->new TravelException("Trip not found!"));
        return tripConverter.convert(list);
    }

    public List<TripDto> searchTripByDate(String date){
        List<Trip> list = tripRepository.findByDate(date).
                orElseThrow(()->new TravelException("Trip not found!"));
        return tripConverter.convert(list);
    }

    public List<TripDto> searchTripByVehicleType(VehicleType vehicleType){
        List<Trip> list = tripRepository.findByVehicleType(vehicleType).
                orElseThrow(()->new TravelException("Trip not found!"));
        return tripConverter.convert(list);
    }

    @Autowired
    public TripService(TripRepository tripRepository, UserRepository userRepository, TripConverter tripConverter) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.tripConverter = tripConverter;
    }
}
