package com.travel.travel.Dto;

import com.travel.travel.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private String fromCity;
    private String toCity;
    private String date;
    private VehicleType vehicleType;
}