package com.example.email.service;

import com.example.email.Dto.NotificationDto;
import com.example.email.repository.EmailRepository;
import com.example.email.repository.NotificationDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationDtoRepository notificationDtoRepository;

    public void sendNotification(NotificationDto notificationDto){
        notificationDtoRepository.save(notificationDto);
    }


}
