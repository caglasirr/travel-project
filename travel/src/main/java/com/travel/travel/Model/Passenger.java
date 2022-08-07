package com.travel.travel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travel.travel.Enum.Sex;
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
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @OneToOne(mappedBy = "passenger")
    private Ticket ticket;
}
