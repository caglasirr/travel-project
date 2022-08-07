package com.travel.travel.Service;

import com.travel.travel.Converter.TripConverter;
import com.travel.travel.Dto.TripDto;
import com.travel.travel.Enum.TripStatus;
import com.travel.travel.Enum.VehicleType;
import com.travel.travel.Exception.TravelException;
import com.travel.travel.Model.Trip;
import com.travel.travel.Repository.TripRepository;
import com.travel.travel.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@SpringBootTest
public class TripServiceTest {

    @InjectMocks
    private TripService tripService;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TripConverter tripConverter;

    @Test
    void itShouldFindTrips_whenFromCityAndToCityAreGiven(){
        //given
        List<Trip> trips = prepareTrips();
        List<TripDto> trips_ = prepareTripDtos();
        Mockito.when(tripRepository.findByFromCityAndToCity("İstanbul","Ankara"))
                .thenReturn(Optional.of(trips));
        Mockito.when(tripConverter.convert(trips)).thenReturn(trips_);

        //when
        List<TripDto> list = tripService.searchTripByCity("İstanbul","Ankara");

        //then
        assertEquals(list,trips_);
        verify(tripRepository).findByFromCityAndToCity("İstanbul","Ankara");
        verify(tripConverter).convert(trips);

    }

    @Test
    void itShouldFindTrips_whenVehicleTypeIsGicen(){
        //given
        List<Trip> trips = prepareTrips();
        List<TripDto> trips_ = prepareTripDtos();
        Mockito.when(tripRepository.findByVehicleType(VehicleType.PLANE))
                .thenReturn(Optional.of(trips));
        Mockito.when(tripConverter.convert(trips)).thenReturn(trips_);

        //when
        List<TripDto> list = tripService.searchTripByVehicleType(VehicleType.PLANE);

        //then
        assertEquals(list,trips_);
        verify(tripRepository).findByVehicleType(VehicleType.PLANE);
        verify(tripConverter).convert(trips);

    }

    @Test
    void itShouldFindTrips_whenDateIsGiven(){
        //given
        List<Trip> trips = prepareTrips();
        List<TripDto> trips_ = prepareTripDtos();
        Mockito.when(tripRepository.findByDate("14.06.1998"))
                .thenReturn(Optional.of(trips));
        Mockito.when(tripConverter.convert(trips)).thenReturn(trips_);

        //when
        List<TripDto> list = tripService.searchTripByDate("14.06.1998");

        //then
        assertEquals(list,trips_);
        verify(tripRepository).findByDate("14.06.1998");
        verify(tripConverter).convert(trips);

    }

    @Test
    void itShouldThrowTravelException_whenFromCityAndToCityAreGivenAndTripNotFound(){
        //given
        Mockito.when(tripRepository.findByFromCityAndToCity("İstanbul","Ankara"))
                .thenReturn(Optional.empty());

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->tripService.searchTripByCity("İstanbul","Ankara"));

        //then
        assertEquals("Trip not found!",e.getMessage());
        verify(tripRepository).findByFromCityAndToCity("İstanbul","Ankara");
        verifyNoInteractions(tripConverter);

    }

    @Test
    void itShouldThrowTravelException_whenVehicleTypeGivenAndTripNotFound(){
        //given
        Mockito.when(tripRepository.findByVehicleType(VehicleType.PLANE))
                .thenReturn(Optional.empty());

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->tripService.searchTripByVehicleType(VehicleType.PLANE));

        //then
        assertEquals("Trip not found!",e.getMessage());
        verify(tripRepository).findByVehicleType(VehicleType.PLANE);
        verifyNoInteractions(tripConverter);

    }

    @Test
    void itShouldThrowTravelException_whenDateIsGivenAndTripNotFound(){
        //given
        Mockito.when(tripRepository.findByDate("14.06.1998"))
                .thenReturn(Optional.empty());

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->tripService.searchTripByDate("14.06.1998"));

        //then
        assertEquals("Trip not found!",e.getMessage());
        verify(tripRepository).findByDate("14.06.1998");
        verifyNoInteractions(tripConverter);

    }

    private List<Trip> prepareTrips(){
        List list = new ArrayList<>();
        Trip trip1 = Trip.builder().fromCity("İstanbul").toCity("Ankara").vehicleType(VehicleType.PLANE)
                .tripStatus(TripStatus.ACTIVE).date("14.06.2022").build();
        Trip trip2 = Trip.builder().fromCity("İstanbul").toCity("Ankara").vehicleType(VehicleType.PLANE)
                .tripStatus(TripStatus.ACTIVE).date("17.06.2022").build();
        list.add(trip1);
        list.add(trip2);
        return list;

    }

    private List<TripDto> prepareTripDtos(){
        List list = new ArrayList<>();
        TripDto trip1 = TripDto.builder().fromCity("İstanbul").toCity("Ankara").vehicleType(VehicleType.PLANE)
                .date("14.06.2022").build();
        TripDto trip2 = TripDto.builder().fromCity("İstanbul").toCity("Ankara").vehicleType(VehicleType.PLANE)
                .date("17.06.2022").build();
        list.add(trip1);
        list.add(trip2);
        return list;

    }

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
