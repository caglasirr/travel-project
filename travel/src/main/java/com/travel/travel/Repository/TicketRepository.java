package com.travel.travel.Repository;

import com.travel.travel.Model.Ticket;
import com.travel.travel.Model.Trip;
import com.travel.travel.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {

    Optional<List<Ticket>> findByUser(User user);
    Optional<List<Ticket>> findByTrip(Trip trip);
    Optional<List<Ticket>> findByUserAndTrip(User user, Trip trip);
}
