package com.example.persondata.mapper;

import com.example.persondata.dto.HouseDto;
import com.example.persondata.dto.PersonDto;
import com.example.persondata.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class PersonMapper {
    private final Mapper mapper;

    @Autowired
    public PersonMapper(Mapper mapper) {
        this.mapper = mapper;
    }


    public PersonEntity mapPersonDtoToPersonEntity(PersonDto personDto) {
        PersonEntity person = mapper.map(personDto, PersonEntity.class);
        if (person.getCars() == null)
            person.setCars(new ArrayList<>());
        if (person.getHouses() == null)
            person.setHouses(new HashSet<>());
        person.setPassport(createNewPassport(personDto, person));
        return person;
    }

    public PersonEntity updatePersonByDto(PersonEntity personEntity, PersonDto personDto) {
        PassportEntity passport = personEntity.getPassport();
        if (isChangePersonalData(personDto)) {
            passport = createNewPassport(personDto, personEntity);
        }
        return PersonEntity.builder()
                .firstName(personDto.getFirstName() == null
                        ? personEntity.getFirstName() : personDto.getFirstName())
                .surname(personDto.getSurname() == null
                        ? personEntity.getSurname() : personDto.getSurname())
                .houses(personDto.getHouses() == null
                        ? personEntity.getHouses()
                        : mapHousesDtoToHousesEntity(personDto.getHouses()))
                .cars(personDto.getCars() == null
                        ? personEntity.getCars() : mapper.mapCollections(personDto.getCars(), CarEntity.class))
                .passport(passport)
                .id(personEntity.getId())
                .gender(personDto.getGender() == null ? personEntity.getGender() : mapper.map(personDto.getGender(), Gender.class))
                .build();
    }


    private Set<HouseEntity> mapHousesDtoToHousesEntity(Set<HouseDto> housesDto) {
        if (housesDto.isEmpty())
            return new HashSet<>();
        return new HashSet<HouseEntity>(mapper.mapCollections(new ArrayList<>(housesDto), HouseEntity.class));

    }

    private PassportEntity createNewPassport(PersonDto personDto, PersonEntity personEntity) {
        return PassportEntity.builder()
                .name(personDto.getFirstName() == null ?
                        personEntity.getFirstName() : personDto.getFirstName())
                .gender(personDto.getGender() == null ?
                        personEntity.getGender() : personDto.getGender())
                .surname(personDto.getSurname() == null ?
                        personEntity.getSurname() : personDto.getSurname())
                .issueDate(LocalDate.now())
                .validTill(LocalDate.now().plusYears(20))
                .build();
    }

    private boolean isChangePersonalData(PersonDto personDto) {
        return personDto.getFirstName() != null || personDto.getGender() != null
                || personDto.getSurname() != null;
    }
}
