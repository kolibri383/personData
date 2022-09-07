package com.example.persondata.dao;

import com.example.persondata.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDao extends JpaRepository<CarEntity, Long> {

}
