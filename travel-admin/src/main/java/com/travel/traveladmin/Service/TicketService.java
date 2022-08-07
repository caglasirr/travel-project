package com.travel.traveladmin.Service;

import com.travel.traveladmin.Client.TravelClient;
import com.travel.traveladmin.Dto.TicketResponse;
import com.travel.traveladmin.Enum.Role;
import com.travel.traveladmin.Exception.TravelException;
import com.travel.traveladmin.Model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private TravelClient travelClient;

    public TicketResponse findAmountAndNumberOfTickets(){
        try{
            TicketResponse response = travelClient.findAmountAndNumberOfTickets();
            return response;
        }catch(Exception e){
            throw new TravelException("Sistemsel bir hata oluştu..");
        }
    }

    @Autowired
    public TicketService(TravelClient travelClient) {
        this.travelClient = travelClient;
    }
}
