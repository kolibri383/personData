package com.example.persondata.dto;

import com.example.persondata.transfer.HouseTransfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Data
public class HouseDto {
    @Null(groups = {HouseTransfer.New.class},
            message = "The id field must be null")
    @NotNull(groups = {HouseTransfer.Exist.class},
            message = "The id field must not be null")
    private Long id;
    @NotBlank(groups = {HouseTransfer.New.class,
            HouseTransfer.Exist.class},
            message = "The street field must not be blank")
    private String street;
    @Positive(groups = {HouseTransfer.New.class,
            HouseTransfer.Exist.class},
            message = "The street field must be positive ")
    @NotNull(groups = {HouseTransfer.New.class,
            HouseTransfer.Exist.class},
            message = "The number field must not be null")
    private Integer number;

}
