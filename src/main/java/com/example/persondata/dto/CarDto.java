package com.example.persondata.dto;

import com.example.persondata.transfer.CarTransfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class CarDto {
    @Null(groups = {CarTransfer.New.class,
            CarTransfer.PartialRefreshable.class},
            message = "The id field must be null")
    @NotNull(groups = CarTransfer.Exist.class,
            message = "The id field must not be null")
    private Long id;

    @NotBlank(groups = {CarTransfer.New.class,
            CarTransfer.Exist.class},
            message = "The brand field must not be blank")
    private String brand;

    @NotBlank(groups = {CarTransfer.New.class,
            CarTransfer.Exist.class},
            message = "The model field must not be blank")
    private String model;
}
