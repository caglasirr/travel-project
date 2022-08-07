package com.example.email.Dto;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notification")
public class NotificationDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String toEmail;
    private String toPhoneNumber;
    private String subject;
    private String body;
    private NotificationStatus notificationStatus;

}