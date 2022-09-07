package com.example.persondata.service;

import com.example.persondata.dto.CarDto;
import com.example.persondata.exception.CarNotFoundException;

import java.util.List;

public interface CarService {
    CarDto create(CarDto CarDto);

    List<CarDto> fetchAll();

    CarDto fetch(Long id) throws CarNotFoundException;

    void delete(Long id) throws CarNotFoundException;

    CarDto fullUpdate(CarDto CarDto, Long id) throws CarNotFoundException;

    CarDto partialUpdate(CarDto carDto, Long id) throws CarNotFoundException;
}
