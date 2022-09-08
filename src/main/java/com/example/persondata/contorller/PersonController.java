package com.example.persondata.contorller;


import com.example.persondata.dto.*;
import com.example.persondata.exception.PersonHouseAddressModificationException;
import com.example.persondata.service.PersonService;
import com.example.persondata.transfer.CarTransfer;
import com.example.persondata.transfer.PersonTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/persons")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public ResponseEntity<List<PersonDto>> fetchAll() {
        return ResponseEntity.ok(personService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> fetch(@PathVariable Long id) {
        return ResponseEntity.ok(personService.fetch(id));
    }

    @GetMapping("/{id}/houses")
    public ResponseEntity<List<HouseDto>> fetchAllHousesByPersonId(@PathVariable Long id) {
        return ResponseEntity.ok(personService.fetchPersonHouses(id));
    }

    @GetMapping("/{id}/cars")
    public ResponseEntity<List<CarDto>> fetchAllCarsByPersonId(@PathVariable Long id) {
        return ResponseEntity.ok(personService.fetchPersonCars(id));
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<CarDto> fetchPersonCarById(@PathVariable Long carId) {
        return ResponseEntity.ok(personService.fetchPersonCarById(carId));
    }

    @GetMapping("/houses/{street}")
    public ResponseEntity<List<PersonDto>> fetchAllPersonsByStreet(@PathVariable String street) {
        return ResponseEntity.ok(personService.fetchAllPersonsByStreet(street));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PassportDto>> fetchAllPassportsByGenderAndFirstLetterSurname(
            @RequestParam(value = "startWith") Character letter
    ) {
        return ResponseEntity.ok(personService.fetchPassportsWhosePersonsSurnamesStartWitchAndGender(letter));
    }

    @GetMapping("/{personId}/houses/{houseId}")
    public ResponseEntity<HouseDto> fetchPersonHouse(@PathVariable Long personId,
                                                     @PathVariable Long houseId) {
        return ResponseEntity.ok(personService.fetchPersonHouseById(personId, houseId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Validated({PersonTransfer.New.class, CarTransfer.New.class})
                                              @RequestBody PersonDto personDto) {
        var response = new ApiResponse(HttpStatus.CREATED, personService.save(personDto));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> fullUpdate(@Validated(PersonTransfer.Exist.class)
                                                  @RequestBody PersonDto personDto, @PathVariable Long id) {
        var response = new ApiResponse(HttpStatus.OK, personService.fullUpdate(personDto, id));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> partialUpdate(@Validated({PersonTransfer.PartialRefreshable.class})
                                                     @RequestBody PersonDto personDto,
                                                     @PathVariable Long id) throws PersonHouseAddressModificationException {
        var response = new ApiResponse(HttpStatus.UPGRADE_REQUIRED, personService.partialUpdate(personDto, id));
        return ResponseEntity.ok(response);
    }


}

