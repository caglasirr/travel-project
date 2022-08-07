package com.example.email.listener;

import com.example.email.Dto.Email;
import com.example.email.Dto.NotificationDto;
import com.example.email.Dto.NotificationStatus;
import com.example.email.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailListener {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "travel.email")
    public void emailListener(NotificationDto notificationDto){
        //log.info("email address: {}", notificationDto.getToEmail());
        if(notificationDto.getNotificationStatus().equals(NotificationStatus.EMAIL)){
            notificationService.sendNotification(notificationDto);
        }

    }
}