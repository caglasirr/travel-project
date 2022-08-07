package com.travel.travel.Service;

import com.travel.travel.Client.PaymentClient;
import com.travel.travel.Converter.OrderConverter;
import com.travel.travel.Dto.OrderDto;
import com.travel.travel.Dto.PaymentDto;
import com.travel.travel.Dto.Request.TicketRequest;
import com.travel.travel.Enum.*;
import com.travel.travel.Exception.TravelException;
import com.travel.travel.Model.*;
import com.travel.travel.Repository.OrderRepository;
import com.travel.travel.Repository.TicketRepository;
import com.travel.travel.Repository.TripRepository;
import com.travel.travel.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private TripRepository tripRepository;
    private OrderRepository orderRepository;
    private PaymentClient paymentClient;
    private OrderConverter orderConverter;

    private final int BUS_CAPACITY = 45;
    private final int PLANE_CAPACITY = 189;
    private final int ONE_TICKET_AMOUNT = 30;
    private final int MALE_PASSENGER_LIMIT = 2;
    private final int RETAIL_USER_TICKET_LIMIT = 5;
    private final int CORPORATE_USER_TICKET_LIMIT = 20;

    @Transactional
    public OrderDto createOrder(int userId, TicketRequest ticketRequest) {
        User user = userRepository.findById(userId).orElseThrow(()->new TravelException("User not found!"));
        Trip trip = tripRepository.findById(ticketRequest.getTripId()).orElseThrow(()->new TravelException("Trip not found!"));
        int ticketNumber = findTicketNumberByUserAndTrip(user,trip);
        int currentCapacity = findCurrentCapacity(trip,ticketRequest);
        int malePassengerNumber = findMalePassengerNumber(ticketRequest);
        List<Ticket> ticketList = new ArrayList<>();
        PaymentDto paymentDto = new PaymentDto();
        Order order = new Order();

        //kontrols
        if(trip.getTripStatus().equals(TripStatus.CANCELLED))
            throw new TravelException("You can not buy ticket for this trip since it is cancelled!");

        if(user.getUserType().equals(UserType.RETAIL) && ticketNumber >= RETAIL_USER_TICKET_LIMIT){
            throw new TravelException("Bireysel kullanıcı aynı sefer için en fazla 5 bilet alabilir.");
        }else if(user.getUserType().equals(UserType.CORPORATE) && ticketNumber >= CORPORATE_USER_TICKET_LIMIT){
            throw new TravelException("Kurumsal kullanıcı aynı sefer için en fazla 20 bilet alabilir.");
        }

        if(trip.getVehicleType().equals(VehicleType.BUS) && currentCapacity >BUS_CAPACITY ){
            throw new TravelException("You can not buy this ticket since capacity is full!");
        }
        if(trip.getVehicleType().equals(VehicleType.PLANE) && currentCapacity >PLANE_CAPACITY){
            throw new TravelException("You can not buy this ticket since capacity is full!");
        }
        if(user.getUserType().equals(UserType.RETAIL) && malePassengerNumber > MALE_PASSENGER_LIMIT){
            throw new TravelException("Bireysel kullanıcı tek bir siparişte en fazla 2 erkek yolcu için bilet alabilir.");
        }

        for(Passenger passenger: ticketRequest.getPassenger()){
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setTrip(trip);
            ticket.setPaymentStatus(PaymentStatus.COMPLETED);
            ticket.setTotalAmount(BigInteger.valueOf(ONE_TICKET_AMOUNT));
            ticket.setPassenger(passenger);
            ticketList.add(ticket);
        }

        order.setUser(user);
        order.setPaymentType(ticketRequest.getPaymentType());
        order.setTotalAmount(BigInteger.valueOf(findOrderAmount(ticketRequest)));
        order.setTickets(ticketList);

        paymentDto.setUserId(userId);
        paymentDto.setPaymentDate(LocalDateTime.now());
        paymentDto.setCurrencyType(CurrencyType.EUR);
        paymentDto.setAmount(BigDecimal.valueOf(findOrderAmount(ticketRequest)));

        try{
            paymentClient.createPayment(paymentDto);
            orderRepository.save(order);
            return orderConverter.convert(order);
        }catch (Exception e){
            throw new TravelException("Sistemsel bir hata oluştu..");
        }
    }

    public List<OrderDto> findTickets(int userId){
        User user = userRepository.findById(userId).orElseThrow(()->new TravelException("User not found!"));
        List<Order> orders = orderRepository.findByUser(user);
        return orderConverter.convert(orders);
    }

    public int findMalePassengerNumber(TicketRequest request){
        return request.getPassenger().stream().filter(t->t.getSex().
                equals(Sex.MAN)).collect(Collectors.toList()).size();
    }

    public int findOrderAmount(TicketRequest request){
        int numberOfPassengers = request.getPassenger().size();
        return ONE_TICKET_AMOUNT*numberOfPassengers;
    }

    public int findCurrentCapacity(Trip trip, TicketRequest request){
        int numberOfTickets = findNumberOfTicketsByTrip(trip);
        int numberOfPassengers = request.getPassenger().size();
        return numberOfPassengers+numberOfTickets;
    }

    public int findNumberOfTicketsByTrip(Trip trip){
        return ticketRepository.findByTrip(trip).get().size();
    }

    public int findTicketNumberByUserAndTrip(User user, Trip trip){
        return ticketRepository.findByUserAndTrip(user,trip).get().size();
    }

    @Autowired
    public OrderService(TicketRepository ticketRepository, UserRepository userRepository,
                        TripRepository tripRepository, OrderRepository orderRepository,
                        PaymentClient paymentClient, OrderConverter orderConverter) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.orderRepository = orderRepository;
        this.paymentClient=paymentClient;
        this.orderConverter=orderConverter;
    }
}
