package com.example.persondata.contorller;

import com.example.persondata.dto.ApiResponse;
import com.example.persondata.dto.HouseDto;
import com.example.persondata.service.HouseService;
import com.example.persondata.transfer.HouseTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/houses")
public class HouseController {
    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse> save(@Validated(HouseTransfer.New.class)
                                            @RequestBody HouseDto houseDto) {
        var houses = houseService.save(houseDto);
        var response = new ApiResponse(HttpStatus.CREATED, houses);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HouseDto>> fetchAll() {
        return ResponseEntity.ok(houseService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDto> fetchById(@Positive
                                              @PathVariable Long id) {
        return ResponseEntity.ok(houseService.fetch(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> fullUpdate(@Validated(HouseTransfer.Exist.class)
                                                  @RequestBody HouseDto houseDto,
                                                  @Positive @PathVariable Long id) {
        var response = new ApiResponse(HttpStatus.OK, houseService.fullUpdate(houseDto, id));
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> partialUpdate(@Validated(HouseTransfer.Exist.class)
                                                     @RequestBody HouseDto houseDto,
                                                     @Positive @PathVariable Long id) {
        var response = new com.example.persondata.dto.ApiResponse(HttpStatus.OK, houseService.partialUpdate(houseDto, id));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        houseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
