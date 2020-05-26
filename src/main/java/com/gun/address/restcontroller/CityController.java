package com.gun.address.restcontroller;

import com.gun.address.dto.CityDistrictProjection;
import com.gun.address.dto.CityDto;
import com.gun.address.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public Page<CityDto> getCities(Pageable pageable) {
        return cityService.findAll(pageable);
    }

    @GetMapping
    @RequestMapping("/getWithDistricts")
    public List<CityDistrictProjection> getWithDistricts() {
        return cityService.findAllWithDistricts();
    }

    @GetMapping("/{cityId}")
    public CityDto getCity(@PathVariable("cityId") int cityId) {
        return cityService.findById(cityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDto saveCity(@Valid @RequestBody CityDto cityDto) {
        return cityService.save(cityDto);
    }

    @PutMapping
    public CityDto updateWithPut(@RequestBody CityDto cityDto) {
        return cityService.update(cityDto);
    }

    @PatchMapping
    public CityDto updateWithPatch(@RequestBody CityDto cityDto) {
        return cityService.updatePartial(cityDto);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    private String handleNotFound(NoSuchElementException ex) {
        return ex.getMessage();
    }

}
