package com.travel.travel.Service;

import com.travel.travel.Converter.UserConvertor;
import com.travel.travel.Dto.NotificationDto;
import com.travel.travel.Dto.Request.UserLoginRequest;
import com.travel.travel.Dto.Request.UserRegisterRequest;
import com.travel.travel.Dto.UserDto;
import com.travel.travel.Enum.NotificationStatus;
import com.travel.travel.Exception.TravelException;
import com.travel.travel.Model.User;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Security.Encryptor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private UserRepository userRepository;
    private Encryptor encryptor;
    private AmqpTemplate rabbitTemplate;
    private MessageSource messageSource;
    private UserConvertor userConvertor;

    public UserDto register(UserRegisterRequest userRegisterRequest) {

        boolean isExists = userRepository.findByEmail(userRegisterRequest.getEmail()).isPresent();
        if(!isExists) {

            User user = userConvertor.convert(userRegisterRequest);
            NotificationDto notificationDto = prepareInfo(user);
            userRepository.save(user);
            rabbitTemplate.convertAndSend("travel.email", "travel.email", notificationDto);

            return userConvertor.convert(user);

        }else{
            throw new TravelException(messageSource.getMessage("user.already.exists",null,Locale.ENGLISH));
        }
    }

    public UserDto login(UserLoginRequest request){
        request.setPassword(encryptor.encryptGivenPassword(request.getPassword()));

        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword()).orElseThrow(
                ()->new TravelException("User not found!")
        );

        return userConvertor.convert(user);
    }

    public NotificationDto prepareInfo(User user){
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setToEmail(user.getEmail());
        notificationDto.setToPhoneNumber(user.getPhoneNumber());
        notificationDto.setSubject("REGISTER");
        notificationDto.setBody("You have registered successfully!");
        notificationDto.setNotificationStatus(NotificationStatus.EMAIL);
        return notificationDto;
    }

    @Autowired
    public UserService(UserRepository userRepository, Encryptor encryptor, AmqpTemplate rabbitTemplate, MessageSource messageSource, UserConvertor userConvertor) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
        this.rabbitTemplate = rabbitTemplate;
        this.messageSource=messageSource;
        this.userConvertor=userConvertor;
    }
}
