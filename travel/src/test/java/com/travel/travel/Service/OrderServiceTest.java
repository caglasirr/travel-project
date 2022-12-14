package com.travel.travel.Service;

import com.travel.travel.Client.PaymentClient;
import com.travel.travel.Converter.OrderConverter;
import com.travel.travel.Dto.NotificationDto;
import com.travel.travel.Dto.OrderDto;
import com.travel.travel.Dto.PaymentDto;
import com.travel.travel.Dto.Request.TicketRequest;
import com.travel.travel.Dto.TicketDto;
import com.travel.travel.Enum.*;
import com.travel.travel.Exception.TravelException;
import com.travel.travel.Model.*;
import com.travel.travel.Repository.OrderRepository;
import com.travel.travel.Repository.TicketRepository;
import com.travel.travel.Repository.TripRepository;
import com.travel.travel.Repository.UserRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PaymentClient paymentClient;
    @Mock
    private OrderConverter orderConverter;
    @Mock
    private AmqpTemplate rabbitTemplate;

    @Test
    void itShouldThrowTravelException_whenUserNotFound(){
        //given
        int userId = 7;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,Mockito.any()));

        //then
        assertEquals("User not found!",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verifyNoInteractions(tripRepository);
        verifyNoInteractions(ticketRepository);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldThrowTravelException_whenTripNotFound(){
        //given
        final int userId = 7;
        final int tripId = 7;
        User user = prepareRetailUser();
        TicketRequest request = prepareTicketRequest();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("Trip not found!",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verifyNoInteractions(ticketRepository);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldThrowTravelException_whenTripStatusIsCancelled(){
        //given
        final int userId = 7;
        final int tripId = 7;
        User user = prepareRetailUser();
        Trip trip = prepareCancelledTrip();
        List<Ticket> ticketList = mock(List.class);
        TicketRequest request = prepareTicketRequest();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList));

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("You can not buy ticket for this trip since it is cancelled!",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository).findByTrip(trip);
        verify(ticketRepository).findByUserAndTrip(user,trip);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldThrowTravelException_whenUsertypeIsRetailAndTicketNumberIsBiggerThanLimit(){
        //given
        final int userId = 7;
        User user = prepareRetailUser();
        Trip trip = prepareBusTrip();
        TicketRequest request = prepareTicketRequest();
        List<Ticket> ticketList = mock(List.class);
        when(ticketList.size()).thenReturn(7);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("Bireysel kullan??c?? ayn?? sefer i??in en fazla 5 bilet alabilir. Kalan bilet hakk??n??z: 0",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository, times(1)).findByUserAndTrip(user,trip);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldThrowTravelException_whenUsertypeIsCorporateAndTicketNumberIsBiggerThanLimit(){
        //given
        final int userId = 7;
        User user = prepareCorporateUser();
        Trip trip = prepareBusTrip();
        TicketRequest request = prepareTicketRequest();
        List<Ticket> ticketList = mock(List.class);
        when(ticketList.size()).thenReturn(21);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList));

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("Kurumsal kullan??c?? ayn?? sefer i??in en fazla 20 bilet alabilir. Kalan bilet hakk??n??z: 0",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository, times(1)).findByUserAndTrip(user,trip);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldThrowTravelException_whenVehicleTypeIsBusAndTicketNumberIsBiggerThanBusLimit(){
        //given
        final int userId = 7;
        User user = prepareCorporateUser();
        Trip trip = prepareBusTrip();
        TicketRequest request = prepareTicketRequest();
        List<Ticket> ticketList = mock(List.class);
        when(ticketList.size()).thenReturn(46);
        List<Ticket> ticketList_ = mock(List.class);
        when(ticketList_.size()).thenReturn(1);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList_));

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("There is only 0 tickets available!",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository, times(1)).findByUserAndTrip(user,trip);
        verify(ticketRepository).findByTrip(trip);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldThrowTravelException_whenVehicleTypeIsPlaneAndTicketNumberIsBiggerThanPlaneLimit(){
        //given
        final int userId = 7;
        User user = prepareCorporateUser();
        Trip trip = preparePlaneTrip();
        TicketRequest request = prepareTicketRequest();
        List<Ticket> ticketList = mock(List.class);
        when(ticketList.size()).thenReturn(189);
        List<Ticket> ticketList_ = mock(List.class);
        when(ticketList_.size()).thenReturn(1);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList_));

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("There is only 0 tickets available!",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository).findByUserAndTrip(user,trip);
        verify(ticketRepository).findByTrip(trip);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldThrowTravelException_whenUserTypeIsRetailAndMalePassengerNumberIsBiggerThanLimitForOneOrder(){
        //given
        final int userId = 7;
        User user = prepareRetailUser();
        Trip trip = prepareBusTrip();
        TicketRequest request = prepareTicketRequest();
        List<Ticket> ticketList = mock(List.class);
        when(ticketList.size()).thenReturn(10);
        List<Ticket> ticketList_ = mock(List.class);
        when(ticketList_.size()).thenReturn(1);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList_));

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("Bireysel kullan??c?? tek bir sipari??te en fazla 2 erkek yolcu i??in bilet alabilir.",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository, times(1)).findByUserAndTrip(user,trip);
        verify(ticketRepository).findByTrip(trip);
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(paymentClient);
    }

    @Test
    void itShouldCreateOrder(){
        //given
        final int userId = 7;
        User user = prepareCorporateUser();
        Trip trip = prepareBusTrip();
        TicketRequest request = prepareTicketRequest();
        List<Ticket> ticketList = mock(List.class);
        when(ticketList.size()).thenReturn(10);
        List<Ticket> ticketList_ = mock(List.class);
        when(ticketList_.size()).thenReturn(1);
        PaymentDto payment = mock(PaymentDto.class);
        Order order = mock(Order.class);
        OrderDto orderDto = mock(OrderDto.class);
        NotificationDto notificationDto = prepareNotificationDto();

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList_));
        Mockito.when(paymentClient.createPayment(payment)).thenReturn(payment);
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Mockito.when(orderConverter.convert(Mockito.any(Order.class))).thenReturn(orderDto);
        Mockito.when(orderConverter.convert(order).toString()).thenReturn("orderDto");
        doNothing().when(rabbitTemplate).convertAndSend(Mockito.anyString(),Mockito.anyString(),Mockito.any(NotificationDto.class));

        //when
        OrderDto response = orderService.createOrder(userId,request);

        //then
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository).findByUserAndTrip(user,trip);
        verify(ticketRepository).findByTrip(trip);
        verify(orderRepository,times(1)).save(Mockito.any());
        verify(rabbitTemplate, times(1)).convertAndSend(Mockito.eq("travel.email"),
                Mockito.eq("travel.email"), Mockito.any(NotificationDto.class));
        verify(paymentClient).createPayment(Mockito.any());
        verify(orderConverter).convert(order);
    }

    @Test
    void itShouldThrowException_whenCanNotGoPaymentClient(){
        //given
        final int userId = 7;
        User user = prepareCorporateUser();
        Trip trip = prepareBusTrip();
        TicketRequest request = prepareTicketRequest();
        List<Ticket> ticketList = mock(List.class);
        when(ticketList.size()).thenReturn(10);
        List<Ticket> ticketList_ = mock(List.class);
        when(ticketList_.size()).thenReturn(1);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(tripRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(trip));
        Mockito.when(ticketRepository.findByTrip(trip)).thenReturn(Optional.of(ticketList));
        Mockito.when(ticketRepository.findByUserAndTrip(user,trip)).thenReturn(Optional.of(ticketList_));
        doNothing().when(rabbitTemplate).convertAndSend(Mockito.anyString(),Mockito.anyString(),Mockito.any(NotificationDto.class));
        Mockito.when(paymentClient.createPayment(Mockito.any())).thenThrow(new RuntimeException());
        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.createOrder(userId,request));

        //then
        assertEquals("Sistemsel bir hata olu??tu..",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verify(tripRepository).findById(Mockito.anyInt());
        verify(ticketRepository).findByUserAndTrip(user,trip);
        verify(ticketRepository).findByTrip(trip);
        verify(paymentClient).createPayment(Mockito.any());
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(orderConverter);
    }

    @Test
    void itShouldThrowTravelException_WhenUserNotFound(){
        //given
        int userId = 7;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->orderService.findTickets(userId));

        //then
        assertEquals("User not found!",e.getMessage());
        verify(userRepository).findById(Mockito.anyInt());
        verifyNoInteractions(orderRepository);
        verifyNoInteractions(orderConverter);
    }

    @Test
    void itShouldfindTickets_WhenUserExists(){
        //given
        int userId = 7;
        User user = prepareRetailUser();
        List<Order> orders = mock(List.class);
        List<OrderDto> orderDtos_ = mock(List.class);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(orderRepository.findByUser(user)).thenReturn(orders);
        Mockito.when(orderConverter.convert(orders)).thenReturn(orderDtos_);
        //when
        List<OrderDto> orderDtos = orderService.findTickets(userId);

        //then
        verify(userRepository).findById(Mockito.anyInt());
        verify(orderRepository).findByUser(user);
        verify(orderConverter).convert(orders);
    }

    private User prepareRetailUser(){
        return User.builder()
                .name("??a??la")
                .surname("S??r")
                .email("selamasqo@gmail.com")
                .password("5f4dcc3b5aa7fkdkf9")
                .phoneNumber("5342728484")
                .userType(UserType.RETAIL).
                role(Role.USER).build();
    }

    private User prepareCorporateUser(){
        return User.builder()
                .name("??a??la")
                .surname("S??r")
                .email("selamasqo@gmail.com")
                .password("5f4dcc3b5aa7fkdkf9")
                .phoneNumber("5342728484")
                .userType(UserType.CORPORATE).
                role(Role.USER).build();
    }

    private NotificationDto prepareNotificationDto(){
        return NotificationDto.builder().toEmail("dd").toPhoneNumber("444")
                .subject("ww").body("s").build();
    }

    private Trip prepareBusTrip(){
        return Trip.builder().fromCity("??stanbul").toCity("Ankara").vehicleType(VehicleType.BUS)
                .tripStatus(TripStatus.ACTIVE).date("14.06.2022").build();
    }

    private Trip preparePlaneTrip(){
        return Trip.builder().fromCity("??stanbul").toCity("Ankara").vehicleType(VehicleType.PLANE)
                .tripStatus(TripStatus.ACTIVE).date("14.06.2022").build();
    }

    private Trip prepareCancelledTrip(){
        return Trip.builder().fromCity("??stanbul").toCity("Ankara").vehicleType(VehicleType.PLANE)
                .tripStatus(TripStatus.CANCELLED).date("14.06.2022").build();
    }

    private TicketRequest prepareTicketRequest(){
        return TicketRequest.builder().tripId(7).paymentType(PaymentType.EFT).passenger(preparePassengers()).build();
    }

    private List<Passenger> preparePassengers(){
        List list = new ArrayList<>();
        Passenger passenger1 = Passenger.builder().name("Zeynep").surname("S??r").sex(Sex.MAN).build();
        Passenger passenger2 = Passenger.builder().name("G??lay").surname("S??r").sex(Sex.MAN).build();
        Passenger passenger3 = Passenger.builder().name("G??lay").surname("S??r").sex(Sex.MAN).build();
        list.add(passenger1);
        list.add(passenger2);
        list.add(passenger3);
        return list;
    }

    private OrderDto prepareOrderDto(){
        return OrderDto.builder().orderNumber(1).paymentType(PaymentType.EFT)
                .totalPaymentAmount(BigInteger.valueOf(90)).tickets(prepareTickets()).build();
    }

    private List<TicketDto> prepareTickets(){
        List list = new ArrayList<>();
        TicketDto ticket1 = TicketDto.builder().ticketNumber(1).build();
        TicketDto ticket2 = TicketDto.builder().ticketNumber(1).build();
        list.add(ticket1);
        list.add(ticket2);
        return list;
    }

}
