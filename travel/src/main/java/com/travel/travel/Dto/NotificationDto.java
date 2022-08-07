package com.travel.travel.Dto;

import com.travel.travel.Enum.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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
