package com.travel.travel.Service;

import com.travel.travel.Converter.UserConvertor;
import com.travel.travel.Dto.NotificationDto;
import com.travel.travel.Dto.Request.UserLoginRequest;
import com.travel.travel.Dto.Request.UserRegisterRequest;
import com.travel.travel.Dto.UserDto;
import com.travel.travel.Enum.Role;
import com.travel.travel.Enum.UserType;
import com.travel.travel.Exception.TravelException;
import com.travel.travel.Model.User;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Security.Encryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private Encryptor encryptor;
    @Mock
    private AmqpTemplate rabbitTemplate;
    @Mock
    private UserConvertor userConvertor;
    @Mock
    private MessageSource messageSource;

    @Test
    @DisplayName("it should create user")
    void itShouldCreateUser_whenUserNotExists() {

        //given
        UserRegisterRequest request = prepareUserRegisterRequest();
        User user = prepareUser();
        UserDto userDto = prepareUserDto(user);

        Mockito.when(userConvertor.convert(request)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userConvertor.convert(user)).thenReturn(userDto);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //when
        UserDto response = userService.register(request);

        //then
        assertEquals(response,userDto);
        verify(userRepository).save(user);
        verify(rabbitTemplate, times(1)).convertAndSend(Mockito.eq("travel.email"), Mockito.eq("travel.email"), Mockito.any(NotificationDto.class));
        verify(userConvertor).convert(request);
        verify(userConvertor).convert(user);
    }

    @Test
    void itShouldThrowTravelException_whenUserAlreadyExists() {

        //given
        UserRegisterRequest request = prepareUserRegisterRequest();
        User user = prepareUser();

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(messageSource.getMessage("user.already.exists",null, Locale.ENGLISH)).thenReturn("User already exists!");

        //when
        TravelException e = assertThrows(TravelException.class, ()->userService.register(request));

        //then
        assertEquals("User already exists!",e.getMessage());
        verifyNoInteractions(encryptor);
        verifyNoInteractions(rabbitTemplate);
        verifyNoInteractions(userConvertor);
    }

    @Test
    void itShouldLogin_whenEmailAndPasswordOk(){

        //given
        UserLoginRequest request = UserLoginRequest.builder().email("deneme@gmail.com")
                .password("password").build();

        User user = prepareUser();
        UserDto userDto = prepareUserDto(user);

        Mockito.when(userConvertor.convert(user)).thenReturn(userDto);
        Mockito.when(encryptor.encryptGivenPassword(request.getPassword())).thenReturn("5f4dcc3b5aa7fkdkf9");
        Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(),Mockito.anyString())).
                thenReturn(Optional.of(user));

        //when
        UserDto response = userService.login(request);

        //then
        assertEquals(response,userDto);
        verify(userConvertor).convert(user);
        verify(encryptor).encryptGivenPassword("password");
        verify(userRepository).findByEmailAndPassword(request.getEmail(),"5f4dcc3b5aa7fkdkf9");


    }

    @Test
    void itShouldThrowTravelException_whenEmailAndPasswordNotOk(){
        //given
        UserLoginRequest request = UserLoginRequest.builder().email("deneme@gmail.com")
                .password("password").build();

        Mockito.when(encryptor.encryptGivenPassword(request.getPassword())).thenReturn("5f4dcc3b5aa7fkdkf9");
        Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(),Mockito.anyString())).
                thenReturn(Optional.empty());

        //when
        TravelException e = assertThrows(TravelException.class, ()->userService.login(request));

        //then
        assertEquals("User not found!",e.getMessage());
        verifyNoInteractions(userConvertor);
        verify(encryptor).encryptGivenPassword("password");
        verify(userRepository).findByEmailAndPassword(request.getEmail(),"5f4dcc3b5aa7fkdkf9");
    }

    private UserRegisterRequest prepareUserRegisterRequest() {
        return UserRegisterRequest.builder()
                .name("Çağla")
                .surname("Sır")
                .email("selamasqo@gmail.com")
                .password("password")
                .phoneNumber("5342728484")
                .userType(UserType.RETAIL)
                .build();
    }

    private User prepareUser(){
        return User.builder()
                .name("Çağla")
                .surname("Sır")
                .email("selamasqo@gmail.com")
                .password("5f4dcc3b5aa7fkdkf9")
                .phoneNumber("5342728484")
                .userType(UserType.RETAIL).
                role(Role.USER).build();
    }

    private UserDto prepareUserDto(User user){
        return UserDto.builder().name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType())
                .build();
    }
}