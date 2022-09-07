package com.example.persondata.dto;

import com.example.persondata.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassportDto {
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate issueDate;
    private LocalDate validTill;
}
