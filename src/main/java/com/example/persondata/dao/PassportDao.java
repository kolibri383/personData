package com.example.persondata.dao;

import com.example.persondata.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportDao extends JpaRepository<PassportEntity, Long> {
}
