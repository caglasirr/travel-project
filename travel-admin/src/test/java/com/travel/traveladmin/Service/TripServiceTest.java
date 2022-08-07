package com.travel.traveladmin.Service;

import com.travel.traveladmin.Client.TravelClient;
import com.travel.traveladmin.Converter.TripConverter;
import com.travel.traveladmin.Dto.TripRequest;
import com.travel.traveladmin.Enum.TripStatus;
import com.travel.traveladmin.Enum.VehicleType;
import com.travel.traveladmin.Exception.TravelException;
import com.travel.traveladmin.Model.Admin;
import com.travel.traveladmin.Model.Trip;
import com.travel.traveladmin.Repository.AdminRepository;
import com.travel.traveladmin.Repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TripServiceTest {

    @InjectMocks
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private TravelClient travelClient;
    @Mock
    private TripConverter tripConverter;

    @Test
    void itShouldAddTrip_whenTripNotExists(){
        //given
        TripRequest request = prepareTripRequest();
        Trip trip = prepareActiveTrip();
        Admin admin = mock(Admin.class);
        Mockito.when(adminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(admin));
        Mockito.when(tripRepository.findByFromCityAndToCityAndDateAndVehicleType
                ("İstanbul","Ankara","14.06.1998",VehicleType.PLANE))
                .thenReturn(Optional.empty());
        Mockito.when(tripConverter.convert(request)).thenReturn(trip);
        Mockito.when(tripRepository.save(trip)).thenReturn(trip);
        Mockito.when(tripConverter.convert(trip)).thenReturn(request);

        //when
        TripRequest response = tripService.addTrip(Mockito.anyInt(),request);

        //then
        assertEquals(request,response);
        verify(adminRepository).findById(Mockito.anyInt());
        verify(tripRepository).save(trip);
        verify(tripConverter).convert(request);
        verify(tripConverter).convert(trip);
        verify(tripRepository).findByFromCityAndToCityAndDateAndVehicleType("İstanbul","Ankara","14.06.1998",VehicleType.PLANE);
    }

    @Test
    void itShouldChangeTripStatusToActive_whenCancelledTripAddAgain(){
        //given
        TripRequest request = prepareTripRequest();
        Trip trip = prepareCancelledTrip();
        Admin admin = mock(Admin.class);
        Mockito.when(adminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(admin));
        Mockito.when(tripRepository.findByFromCityAndToCityAndDateAndVehicleType
                        ("İstanbul","Ankara","14.06.1998",VehicleType.PLANE))
                .thenReturn(Optional.of(trip));
        Mockito.when(tripRepository.save(trip)).thenReturn(trip);
        Mockito.when(tripConverter.convert(trip)).thenReturn(request);

        //when
        TripRequest response = tripService.addTrip(Mockito.anyInt(),request);

        //then
        assertEquals(request,response);
        verify(adminRepository).findById(Mockito.anyInt());
        verify(tripRepository).save(trip);
        verify(tripConverter).convert(trip);
        verify(tripRepository,times(2)).findByFromCityAndToCityAndDateAndVehicleType("İstanbul","Ankara","14.06.1998",VehicleType.PLANE);
    }

    @Test
    void itShouldThrowTravelException_whenTripExists(){
        //given
        TripRequest request = prepareTripRequest();
        Trip trip = prepareActiveTrip();
        Admin admin = mock(Admin.class);
        Mockito.when(adminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(admin));
        Mockito.when(tripRepository.findByFromCityAndToCityAndDateAndVehicleType
                        ("İstanbul","Ankara","14.06.1998",VehicleType.PLANE))
                .thenReturn(Optional.of(trip));

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->tripService.addTrip(Mockito.anyInt(),request));

        //then
        assertEquals("Trip already exists!",e.getMessage());
        verifyNoInteractions(tripConverter);
        verify(adminRepository).findById(Mockito.anyInt());
        verify(tripRepository,times(2)).findByFromCityAndToCityAndDateAndVehicleType("İstanbul","Ankara","14.06.1998",VehicleType.PLANE);
    }

    @Test
    void itShouldCancelTrip_whenTripExistsAndStatusActive(){
        //given
        int tripId = 1;
        int adminId = 1;
        Trip trip = prepareActiveTrip();
        Admin admin = mock(Admin.class);
        TripRequest request = prepareTripRequest();
        Mockito.when(tripRepository.save(trip)).thenReturn(trip);
        Mockito.when(tripConverter.convert(trip)).thenReturn(prepareTripRequest());
        Mockito.when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        Mockito.when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        //when
        TripRequest response = tripService.cancelTrip(adminId,tripId);

        //then
        assertEquals(request,response);
        verify(adminRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(tripRepository).save(trip);
        verify(tripConverter).convert(trip);
    }

    private TripRequest prepareTripRequest(){
        return TripRequest.builder().fromCity("İstanbul")
                .toCity("Ankara").date("14.06.1998").vehicleType(VehicleType.PLANE)
                .build();
    }

    private Trip prepareCancelledTrip(){
        return Trip.builder().fromCity("İstanbul")
                .toCity("Ankara").date("14.06.1998").vehicleType(VehicleType.PLANE)
                .tripStatus(TripStatus.CANCELLED).build();
    }

    private Trip prepareActiveTrip(){
        return Trip.builder().fromCity("İstanbul")
                .toCity("Ankara").date("14.06.1998").vehicleType(VehicleType.PLANE)
                .tripStatus(TripStatus.ACTIVE).build();
    }

}
