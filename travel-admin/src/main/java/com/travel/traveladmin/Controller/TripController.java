package com.travel.traveladmin.Controller;

import com.travel.traveladmin.Dto.TicketResponse;
import com.travel.traveladmin.Dto.TripRequest;
import com.travel.traveladmin.Service.TicketService;
import com.travel.traveladmin.Service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/admins")
public class TripController {

    private TripService tripService;
    private TicketService ticketService;

    @PostMapping(value = "/{adminId}/trip")
    public ResponseEntity<String> addTrip(@PathVariable int adminId, @RequestBody TripRequest tripRequest){
        tripService.addTrip(adminId, tripRequest);
        return new ResponseEntity<>("Trip saved!",HttpStatus.OK);
    }

    @PutMapping(value = "/{adminId}/trip/{tripId}")
    public ResponseEntity<String> cancelTrip(@PathVariable int adminId, @PathVariable int tripId){
        tripService.cancelTrip(adminId,tripId);
        return new ResponseEntity<>("Trip cancelled!",HttpStatus.OK);
    }

    @GetMapping(value = "/tickets")
    public ResponseEntity<TicketResponse> findAmountAndNumberOfTickets(){
        TicketResponse response = ticketService.findAmountAndNumberOfTickets();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Autowired
    public TripController(TripService tripService, TicketService ticketService) {
        this.tripService = tripService;
        this.ticketService = ticketService;
    }
}