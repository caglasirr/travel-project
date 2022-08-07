package com.example.email.repository;

import com.example.email.Dto.NotificationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDtoRepository extends JpaRepository<NotificationDto,Integer> {
}
