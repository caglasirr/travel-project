package com.travel.travel.Dto;

import com.travel.travel.Enum.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PassengerDto {
    private String name;
    private String surname;
    private Sex sex;
}
