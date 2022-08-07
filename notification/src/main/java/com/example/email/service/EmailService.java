package com.example.email.service;

import com.example.email.Dto.Email;
import com.example.email.Dto.NotificationDto;
import com.example.email.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService extends NotificationService{
    @Autowired
    private EmailRepository emailRepository;

    @Override
    public void sendNotification(NotificationDto notificationDto) {
        Email email = new Email();
        notificationDto= email;
        emailRepository.save(email);
    }
}
