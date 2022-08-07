package com.travel.traveladmin.Service;

import com.travel.traveladmin.Client.TravelClient;
import com.travel.traveladmin.Converter.TripConverter;
import com.travel.traveladmin.Dto.TicketResponse;
import com.travel.traveladmin.Dto.TripRequest;
import com.travel.traveladmin.Enum.Role;
import com.travel.traveladmin.Enum.TripStatus;
import com.travel.traveladmin.Exception.TravelException;
import com.travel.traveladmin.Model.Admin;
import com.travel.traveladmin.Model.Trip;
import com.travel.traveladmin.Repository.AdminRepository;
import com.travel.traveladmin.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    private TripRepository tripRepository;
    private AdminRepository adminRepository;
    private TripConverter tripConverter;

    public TripRequest addTrip(int adminId, TripRequest tripRequest){

        Admin admin = adminRepository.findById(adminId).orElseThrow(()->new TravelException("Admin not found!"));
        String fromCity = tripRequest.getFromCity();
        String toCity = tripRequest.getToCity();
        boolean isExistsTrip = tripRepository.findByFromCityAndToCityAndDateAndVehicleType(fromCity,toCity,tripRequest.getDate(),tripRequest.getVehicleType()).isPresent();
                if(!isExistsTrip){
                    Trip trip = tripConverter.convert(tripRequest);
                    trip.setTripStatus(TripStatus.ACTIVE);
                    tripRepository.save(trip);
                    return tripConverter.convert(trip);
                }else{
                    Trip trip = tripRepository.findByFromCityAndToCityAndDateAndVehicleType(fromCity,toCity,tripRequest.getDate(),tripRequest.getVehicleType()).get();
                    if(trip.getTripStatus().equals(TripStatus.CANCELLED)){
                        trip.setTripStatus(TripStatus.ACTIVE);
                        tripRepository.save(trip);
                        return tripConverter.convert(trip);
                    }else{
                        throw new TravelException("Trip already exists!");
                    }
                }
    }

    public TripRequest cancelTrip(int userId, int tripId){
        Admin admin = adminRepository.findById(userId).orElseThrow(()->new TravelException("Admin not found!"));
        Trip trip = tripRepository.findById(tripId).orElseThrow(()->new TravelException("Trip not found!"));
        trip.setTripStatus(TripStatus.CANCELLED);
        tripRepository.save(trip);
        return tripConverter.convert(trip);
    }

    @PostConstruct
    public void init(){
        prepareAdmins();
    }
    public void prepareAdmins(){
        List list = new ArrayList<>();
        Admin admin1 = Admin.builder().id(1).name("Çağla").surname("Sır")
                .email("cagla@gmail.com").password("password").role(Role.ADMIN)
                .build();
        Admin admin2 = Admin.builder().id(2).name("Zeynep").surname("Sır")
                .email("cagla@gmail.com").password("password").role(Role.ADMIN)
                .build();
        list.add(admin1);
        list.add(admin2);
        adminRepository.saveAll(list);
    }

    @Autowired
    public TripService(TripRepository tripRepository, AdminRepository adminRepository, TripConverter tripConverter) {
        this.tripRepository = tripRepository;
        this.adminRepository = adminRepository;
        this.tripConverter = tripConverter;
    }
}
