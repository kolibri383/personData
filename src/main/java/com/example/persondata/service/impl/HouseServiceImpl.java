package com.example.persondata.service.impl;

import com.example.persondata.dao.HouseDao;
import com.example.persondata.dto.HouseDto;
import com.example.persondata.entity.HouseEntity;
import com.example.persondata.exception.HouseChangeIdException;
import com.example.persondata.exception.HouseNotFoundException;
import com.example.persondata.exception.HouseSaveException;
import com.example.persondata.mapper.Mapper;
import com.example.persondata.service.HouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseDao houseDao;
    private final Mapper mapper;

    public HouseServiceImpl(HouseDao houseDao, Mapper mapper) {
        this.houseDao = houseDao;
        this.mapper = mapper;
    }

    @Override
    public HouseDto fetch(Long id) throws HouseNotFoundException {
        var house = houseDao.findById(id)
                .orElseThrow(() ->
                        new HouseNotFoundException("no house found with id" + id));
        return mapper.map(house, HouseDto.class);
    }

    @Override
    public List<HouseDto> fetchAll() {
        return mapper.mapCollections(houseDao.findAll(), HouseDto.class);
    }

    @Override
    public HouseDto save(HouseDto houseDto) {
        if (houseDao.existsByStreetAndAndNumber(houseDto.getStreet(), houseDto.getNumber()))
            throw new HouseSaveException("A house with such data already exists in the database");
        HouseEntity houseEntity = mapper.map(houseDto, HouseEntity.class);
        return mapper.map(houseDao.save(houseEntity), HouseDto.class);
    }

    @Override
    public HouseDto fullUpdate(HouseDto houseDto, Long id) throws HouseNotFoundException {
        checkHouseExistOrException(id);
        if (!houseDto.getId().equals(id))
            throw new HouseChangeIdException("the ID of the house being modified and the provided ID do not match." +
                    " The values of the 'house_id' field cannot be changed");
        var house = mapper.map(houseDto, HouseEntity.class);
        return mapper.map(houseDao.save(house), HouseDto.class);
    }

    @Override
    public HouseDto partialUpdate(HouseDto houseDto, Long id) throws HouseNotFoundException {
        var house = houseDao.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("no house found with id" + id));
        var houseUpdated = HouseEntity.builder()
                .id(house.getId())
                .street(houseDto.getStreet() != null ? house.getStreet() : houseDto.getStreet())
                .number(houseDto.getNumber() != null ? house.getNumber() : houseDto.getNumber())
                .build();
        return mapper.map(houseDao.save(houseUpdated), HouseDto.class);
    }

    @Override
    public void delete(Long id) throws HouseNotFoundException {
        checkHouseExistOrException(id);
        houseDao.existsById(id);
    }


    private void checkHouseExistOrException(Long id) throws HouseNotFoundException {
        if (houseDao.existsById(id))
            throw new HouseNotFoundException("no house found with id" + id);
    }
}
