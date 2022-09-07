package com.example.persondata.service.impl;

import com.example.persondata.dao.PassportDao;
import com.example.persondata.dto.PersonDto;
import com.example.persondata.entity.PassportEntity;
import com.example.persondata.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PassportServiceImpl implements PassportService {
    private final PassportDao passportDao;

    @Autowired
    public PassportServiceImpl(PassportDao passportDao) {
        this.passportDao = passportDao;
    }


    @Override
    public PassportEntity create(PersonDto personDto) {
        return passportDao.save(
                PassportEntity.builder()
                        .issueDate(LocalDate.now())
                        .validTill(LocalDate.now().plusYears(20))
                        .build());
    }

    @Override
    public void delete(Long id) {
        passportDao.deleteById(id);
    }
}
