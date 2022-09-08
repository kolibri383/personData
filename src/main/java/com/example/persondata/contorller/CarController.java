package com.example.persondata.contorller;

import com.example.persondata.dto.ApiResponse;
import com.example.persondata.dto.CarDto;
import com.example.persondata.service.CarService;
import com.example.persondata.transfer.CarTransfer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> fetchAll() {
        return ResponseEntity.ok(carService.fetchAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CarDto> fetchById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.fetch(id));
    }


    @PostMapping
    public ResponseEntity<ApiResponse> create(@Validated({CarTransfer.New.class})
                                         @RequestBody CarDto carDto) {
        var response = new ApiResponse(HttpStatus.CREATED, carService.create(carDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> fullUpdate(@Validated(CarTransfer.Exist.class)
                                             @RequestBody CarDto carDto,
                                             @PathVariable Long id) {
        var response = new ApiResponse(HttpStatus.OK ,carService.fullUpdate(carDto, id));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> partialUpdate(@Validated(CarTransfer.PartialRefreshable.class)
                                                @RequestBody CarDto carDto,
                                                @PathVariable Long id) {
        var response = new  ApiResponse(HttpStatus.OK, carService.partialUpdate(carDto, id));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
