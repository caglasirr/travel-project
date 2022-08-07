package com.example.email.Dto;

import com.example.email.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sms")
public class Sms extends NotificationService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String toPhoneNumber;
}
