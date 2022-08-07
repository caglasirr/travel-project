package com.travel.travel.Controller;

import com.travel.travel.Dto.TripDto;
import com.travel.travel.Enum.VehicleType;
import com.travel.travel.Service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/users")
public class TripController {

    private TripService tripService;

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

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }
}
