package com.travel.travel.Model;

import com.travel.travel.Enum.PaymentStatus;
import com.travel.travel.Enum.TripStatus;
import com.travel.travel.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fromCity;
    private String toCity;
    private String date;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
