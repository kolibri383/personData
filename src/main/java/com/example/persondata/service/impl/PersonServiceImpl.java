package com.example.persondata.service.impl;

import com.example.persondata.dao.HouseDao;
import com.example.persondata.dao.PersonDao;
import com.example.persondata.dto.CarDto;
import com.example.persondata.dto.HouseDto;
import com.example.persondata.dto.PassportDto;
import com.example.persondata.dto.PersonDto;
import com.example.persondata.entity.HouseEntity;
import com.example.persondata.entity.PersonEntity;
import com.example.persondata.exception.*;
import com.example.persondata.mapper.Mapper;
import com.example.persondata.mapper.PersonMapper;
import com.example.persondata.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonDao personDao;
    private final HouseDao houseDao;
    private final Mapper mapper;
    private final PersonMapper personMapper;


    @Autowired
    public PersonServiceImpl(PersonDao personDao, HouseDao houseDao, Mapper mapper, PersonMapper personMapper) {
        this.personDao = personDao;
        this.houseDao = houseDao;
        this.mapper = mapper;
        this.personMapper = personMapper;
    }

    @Override
    @Transactional
    public PersonDto save(PersonDto personDto) {
        PersonEntity person = personMapper.mapPersonDtoToPersonEntity(personDto);
        var houses = mapper.mapCollections(Arrays.asList(person.getHouses().toArray()), HouseEntity.class);
        person.setHouses(new HashSet<>(getIdForExistHouses(houses)));
        return mapper.map(personDao.save(person), PersonDto.class);
    }

    @Override
    public List<PersonDto> fetchAll() {
        return mapper.mapCollections(personDao.findAll(), PersonDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) throws PersonNotFoundException {
        checkIsPersonExistOrThrowError(id);
        personDao.deleteById(id);
    }

    @Override
    public PersonDto fetch(Long id) throws PersonNotFoundException {
        checkIsPersonExistOrThrowError(id);
        var person = personDao.findById(id)
                .orElseThrow(() ->
                        new PersonNotFoundException("no person found with id " + id));
        return mapper.map(person, PersonDto.class);
    }

    @Override
    public boolean existsById(Long id) {
        return personDao.existsById(id);
    }

    @Override
    @Transactional
    public PersonDto fullUpdate(PersonDto personDto, Long id) throws PersonChangeIdException {
        checkIsPersonExistOrThrowError(id);
        if (!personDto.getId().equals(id))
            throw new PersonChangeIdException("the ID of the person being modified and the provided ID do not match." +
                    " The values of the 'person_id' field cannot be changed");
        var person = personMapper.mapPersonDtoToPersonEntity(personDto);
        var houses = mapper.mapCollections(Arrays.asList(person.getHouses().toArray()), HouseEntity.class);
        person.setHouses(new HashSet<>(getIdForExistHouses(houses)));
        return mapper.map(personDao.save(person), PersonDto.class);
    }

    @Override
    public PersonDto partialUpdate(PersonDto personDto, Long id) throws PersonNotFoundException, PersonHouseAddressModificationException {
        var personEntity = personDao.findById(id)
                .orElseThrow(() ->
                        new PersonNotFoundException("no person found with id " + id));
        var updatedPerson = personMapper.updatePersonByDto(personEntity, personDto);
        var houses = mapper.mapCollections(Arrays.asList(personDto.getHouses().toArray()), HouseEntity.class);
        updatedPerson.setHouses(new HashSet<>(getIdForExistHouses(houses)));
        return mapper.map(personDao.save(updatedPerson), PersonDto.class);
    }

    @Override
    public List<CarDto> fetchPersonCars(Long id) throws PersonNotFoundException{
        var cars = personDao.findById(id)
                .orElseThrow(() ->
                        new PersonNotFoundException("no person found with id " + id))
                .getCars();
        return mapper.mapCollections(cars, CarDto.class);
    }

    @Override
    public List<HouseDto> fetchPersonHouses(Long id) throws PersonNotFoundException {
        checkIsPersonExistOrThrowError(id);
        var houses = personDao.findPersonHouses(id);
        return mapper.mapCollections(houses, HouseDto.class);
    }

    @Override
    public CarDto fetchPersonCarById(Long carId) throws CarNotFoundException {
        var car = personDao.findPersonCarById(carId)
                .orElseThrow(() ->
                        new CarNotFoundException("It is impossible to find a car with an id " + carId));
        return mapper.map(car, CarDto.class);
    }

    @Override
    public HouseDto fetchPersonHouseById(Long personId, Long houseId) throws HouseNotFoundException {
        checkIsPersonExistOrThrowError(personId);
        var house = personDao.findPersonHouseById(personId, houseId).
                orElseThrow(() -> new HouseNotFoundException("Person with id " + personId
                        + "haven't house with id " + houseId));
        return mapper.map(house, HouseDto.class);
    }

    @Override
    public List<PersonDto> fetchAllPersonsByStreet(String street) {
        return mapper.mapCollections(personDao.findPersonEntitiesByHousesStreet(street), PersonDto.class);
    }

    @Override
    public List<PassportDto> fetchPassportsWhosePersonsSurnamesStartWitchAndGender(Character letter) {
        var passports = personDao.findAllMalesBySurnameStartWith(letter)
                .stream().map(PersonEntity::getPassport)
                .collect(Collectors.toList());
        return mapper.mapCollections(passports, PassportDto.class);
    }


    private void checkIsPersonExistOrThrowError(Long id) throws PersonNotFoundException {
        if (!personDao.existsById(id))
            throw new PersonNotFoundException("no person found with id " + id);
    }

    private List<HouseEntity> getIdForExistHouses(List<HouseEntity> houses) throws PersonHouseAddressModificationException {
        return houses.stream()
                .map(house -> {
                    var existHouse = houseDao.findByStreetAndNumber(house.getStreet(), house.getNumber());
                    if (existHouse == null && house.getId() != null)
                        throw new PersonHouseAddressModificationException("it is not possible to change the" +
                                " house data in this request." +
                                " You This request can change the data about the possessions of a person," +
                                " but cannot change their essence.");
                    return existHouse == null ? house : existHouse;
                }).collect(Collectors.toList());
    }


}
