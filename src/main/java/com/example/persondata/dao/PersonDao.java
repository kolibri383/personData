package com.example.persondata.dao;

import com.example.persondata.entity.CarEntity;
import com.example.persondata.entity.HouseEntity;
import com.example.persondata.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonDao extends JpaRepository<PersonEntity, Long> {

    @Query("select p.houses from PersonEntity p where p.id =:id")
    List<HouseEntity> findPersonHouses(@Param("id") Long id);

    @Query("select h from PersonEntity as p join p.houses as h where h.id = :houseId and p.id=:personId")
    Optional<HouseEntity> findPersonHouseById(@Param("personId") Long personId, @Param("houseId") Long houseId);

    @Query("select c from PersonEntity as p join p.cars as c where c.id = :carId")
    Optional<CarEntity> findPersonCarById(@Param("carId") Long carId);

    @Query("select p from PersonEntity as p join p.houses as h where h.street =:street")
    List<PersonEntity> findPersonEntitiesByHousesStreet(@Param("street") String street);

    @Query(value = "SELECT * FROM persons as p" +
            " WHERE LOWER(p.surName) LIKE LOWER(:letter||'%') and p.gender='MALE'", nativeQuery = true)
    List<PersonEntity> findAllMalesBySurnameStartWith(@Param("letter") Character letter);

}
