package com.travel.traveladmin.Repository;

import com.travel.traveladmin.Enum.VehicleType;
import com.travel.traveladmin.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip,Integer> {
     Optional<Trip> findByFromCityAndToCityAndDateAndVehicleType(String dc, String ac, String date, VehicleType vt);
     Optional<List<Trip>> findByFromCityAndToCity(String dc, String ac);
     Optional<List<Trip>> findByVehicleType(VehicleType vt);
     Optional<List<Trip>> findByDate(String date);
}
