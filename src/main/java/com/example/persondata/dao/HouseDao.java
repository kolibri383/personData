package com.example.persondata.dao;

import com.example.persondata.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public interface HouseDao extends JpaRepository<HouseEntity, Long> {


    boolean existsByStreetAndAndNumber(@NotBlank String street, @Positive Integer number);

    HouseEntity findByStreetAndNumber(@NotBlank String street, @Positive Integer number);


}
