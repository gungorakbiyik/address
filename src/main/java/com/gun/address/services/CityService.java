package com.gun.address.services;

import com.gun.address.dto.CityDistrictProjection;
import com.gun.address.dto.CityDto;
import com.gun.address.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {
    List<CityDto> findAll();

    List<CityDto> findAllByNameLike(String name);

    Page<CityDto> findAll(Pageable pageable);

    CityDto findById(int cityId);

    City findCityById(int cityId);

    CityDto save(CityDto city);

    CityDto update(CityDto cityDto);

    CityDto updatePartial(CityDto cityDto);

    List<CityDistrictProjection> findAllWithDistricts();

}
