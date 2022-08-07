package com.travel.traveladmin.Dto;

import com.travel.traveladmin.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {
    private String fromCity;
    private String toCity;
    private String date;
    private VehicleType vehicleType;
}