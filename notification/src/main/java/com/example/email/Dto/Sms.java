package com.example.email.Dto;

import com.example.email.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sms extends NotificationService {
    private String toPhoneNumber;
}
