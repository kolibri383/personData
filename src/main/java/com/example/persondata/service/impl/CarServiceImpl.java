package com.example.persondata.service.impl;

import com.example.persondata.dao.CarDao;
import com.example.persondata.dto.CarDto;
import com.example.persondata.entity.CarEntity;
import com.example.persondata.exception.CarChangeIdException;
import com.example.persondata.exception.CarNotFoundException;
import com.example.persondata.mapper.Mapper;
import com.example.persondata.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarDao carDao;
    private final Mapper mapper;

    @Autowired
    public CarServiceImpl(CarDao carDao, Mapper mapper) {
        this.carDao = carDao;
        this.mapper = mapper;
    }

    @Override
    public CarDto create(CarDto carDto) {
        CarEntity car = mapper.map(carDto, CarEntity.class);
        return mapper.map(carDao.save(car), CarDto.class);
    }

    @Override
    public List<CarDto> fetchAll() {
        return mapper.mapCollections(carDao.findAll(), CarDto.class);
    }


    @Override
    public CarDto fetch(Long id) throws CarNotFoundException {
        checkIsCarExistOrThrowError(id);
        return mapper.map(carDao.findById(id), CarDto.class);
    }

    @Override
    public void delete(Long id) throws CarNotFoundException {
        checkIsCarExistOrThrowError(id);
        carDao.deleteById(id);
    }


    @Override
    public CarDto fullUpdate(CarDto carDto, Long id) throws CarNotFoundException, CarChangeIdException {
        checkIsCarExistOrThrowError(id);
        if (!carDto.getId().equals(id))
            throw new CarChangeIdException("the ID of the car being modified and the provided ID do not match." +
                    " The values of the 'car_id' field cannot be changed");
        CarEntity car = mapper.map(carDto, CarEntity.class);
        return mapper.map(carDao.save(car), CarDto.class);
    }

    @Override
    public CarDto partialUpdate(CarDto carDto, Long id) throws CarNotFoundException {
        CarEntity car = carDao.findById(id)
                .orElseThrow(() ->
                        new CarNotFoundException("no car found with id " + id));
        CarEntity carUpdated = CarEntity.builder()
                .id(car.getId())
                .model(carDto.getModel() != null ? carDto.getModel() : car.getModel())
                .brand(carDto.getBrand() != null ? carDto.getBrand() : car.getBrand())
                .build();
        return mapper.map(carDao.save(carUpdated), CarDto.class);
    }


    private void checkIsCarExistOrThrowError(Long id) throws CarNotFoundException {
        if (!carDao.existsById(id))
            throw new CarNotFoundException("no car found with id " + id);
    }


}

