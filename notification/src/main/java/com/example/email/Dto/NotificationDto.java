package com.example.email.Dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private int id;
    private String toEmail;
    private String toPhoneNumber;
    private String subject;
    private String body;
    private NotificationStatus notificationStatus;

}