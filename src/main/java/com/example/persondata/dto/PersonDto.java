package com.example.persondata.dto;

import com.example.persondata.entity.Gender;
import com.example.persondata.transfer.PersonTransfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;

@Data
public class PersonDto {
    @Null(groups = {PersonTransfer.New.class,
            PersonTransfer.PartialRefreshable.class},
            message = "The id field must be null")
    @NotNull(groups = PersonTransfer.Exist.class,
            message = "The id field must not be null")
    private Long id;
    @NotBlank(groups = {PersonTransfer.New.class,
            PersonTransfer.Exist.class},
            message = "The firstName field must not be blank")
    private String firstName;
    @NotBlank(groups = {PersonTransfer.Exist.class,
            PersonTransfer.New.class},
            message = "The surname field must not be blank")
    private String surname;
    @NotNull(groups = {PersonTransfer.New.class, PersonTransfer.Exist.class},
            message = "The gender field must not be null")
    private Gender gender;
    @Null(groups = {PersonTransfer.New.class,
            PersonTransfer.Exist.class},
            message = "The passport field must not be null")
    private PassportDto passport;

    private List<CarDto> cars;

    private Set<HouseDto> houses;
}
