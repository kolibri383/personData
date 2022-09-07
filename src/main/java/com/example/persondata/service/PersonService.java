package com.example.persondata.service;

import com.example.persondata.dto.CarDto;
import com.example.persondata.dto.HouseDto;
import com.example.persondata.dto.PassportDto;
import com.example.persondata.dto.PersonDto;
import com.example.persondata.exception.PersonNotFoundException;

import java.util.List;

public interface PersonService {
    PersonDto save(PersonDto personDto);

    List<PersonDto> fetchAll();

    void delete(Long id) throws PersonNotFoundException;

    PersonDto fetch(Long id) throws PersonNotFoundException;

    boolean existsById(Long id);

    PersonDto fullUpdate(PersonDto personDto, Long id);

    PersonDto partialUpdate(PersonDto personDto, Long id);

    List<CarDto> fetchPersonCars(Long id);

    List<HouseDto> fetchPersonHouses(Long id);

    CarDto fetchPersonCarById(Long carId);

    HouseDto fetchPersonHouseById(Long personId, Long houseId);

    List<PersonDto> fetchAllPersonsByStreet(String street);

    List<PassportDto> fetchPassportsWhosePersonsSurnamesStartWitchAndGender(Character letter);


}
