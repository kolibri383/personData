package com.example.persondata.service;

import com.example.persondata.dto.PersonDto;
import com.example.persondata.entity.PassportEntity;

public interface PassportService {
    PassportEntity create(PersonDto personDto);

    void delete(Long id);
}
