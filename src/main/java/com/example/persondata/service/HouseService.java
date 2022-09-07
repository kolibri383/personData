package com.example.persondata.service;

import com.example.persondata.dto.HouseDto;
import com.example.persondata.dto.PersonDto;
import com.example.persondata.exception.HouseNotFoundException;

import java.util.List;

public interface HouseService {
    HouseDto fetch(Long id) throws HouseNotFoundException;

    List<HouseDto> fetchAll();

    HouseDto save(HouseDto houseDto);

    HouseDto fullUpdate(HouseDto houseDto, Long id);

    HouseDto partialUpdate(HouseDto houseDto, Long id);

    void delete(Long id);
}
