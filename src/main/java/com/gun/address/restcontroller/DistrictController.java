package com.gun.address.restcontroller;

import com.gun.address.dto.DistrictDto;
import com.gun.address.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/cities/{cityId}/districts")
public class DistrictController {

    private DistrictService districtService;

    @Autowired
    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping
    public Page<DistrictDto> getDistricts(@PathVariable("cityId") int cityId, Pageable pageable) {
        return districtService.findByCityId(cityId, pageable);
    }

    @GetMapping
    @RequestMapping("/{districtId}")
    public DistrictDto getDistrict(@PathVariable("cityId") int cityId, @PathVariable("districtId") int districtId) {
        return districtService.findByIdAndCityId(districtId, cityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistrictDto save(@PathVariable("cityId") int cityId, @RequestBody DistrictDto districtDto) {
        return districtService.save(cityId, districtDto);
    }

    @PutMapping
    public DistrictDto updateWithPut(@PathVariable("cityId") int cityId, @RequestBody DistrictDto districtDto) {
        return districtService.update(cityId, districtDto);
    }

    @PatchMapping
    public DistrictDto updateWithPatch(@PathVariable("cityId") int cityId, @RequestBody DistrictDto districtDto) {
        return districtService.updatePartial(cityId, districtDto);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    private String handleNotFound(NoSuchElementException ex) {
        return ex.getMessage();
    }

}
