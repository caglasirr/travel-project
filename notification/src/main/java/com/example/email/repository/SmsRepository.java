package com.example.email.repository;

import com.example.email.Dto.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<Sms,Integer> {

}
