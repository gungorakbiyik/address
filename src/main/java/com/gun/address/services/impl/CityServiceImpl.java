package com.gun.address.services.impl;

import com.gun.address.aop.Loggable;
import com.gun.address.dto.CityDistrictProjection;
import com.gun.address.dto.CityDto;
import com.gun.address.exception.CityUniqueConstraintPlateException;
import com.gun.address.repositories.CityRepository;
import com.gun.address.model.City;
import com.gun.address.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);
    private CityRepository repo;

    @Autowired
    public CityServiceImpl(CityRepository repo) {
        this.repo = repo;
    }

    @Override
    @Loggable
    public List<CityDto> findAll() {
        return repo.findAll()
                .stream()
                .map(city -> toDto(city))
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDto> findAllByNameLike(String name) {
//        return repo.findAllByNameContainingIgnoreCase(name)
        return repo.findAllByNameLikeIgnoreCase(name)
                .stream()
                .map(city -> toDto(city))
                .collect(Collectors.toList());
    }


    @Override
    @Loggable
    public Page<CityDto> findAll(Pageable pageable) {
        Page<City> pageCity = repo.findAll(pageable);
        List<CityDto> cityDtos = pageCity.get()
                .map(city -> toDto(city)).collect(Collectors.toList());
        return new PageImpl<>(cityDtos, pageable, pageCity.getTotalElements());
    }

    @Override
    @Loggable
    public CityDto findById(int cityId) {
        return toDto(findCityById(cityId));
    }

    @Override
    @Loggable
    public City findCityById(int cityId) {
        return repo.findById(cityId)
                .orElseThrow(() -> new NoSuchElementException(String.format("City does not exists %d", cityId)));
    }

    @Override
    @Loggable
    public CityDto save(CityDto cityDto) {
        return toDto(save(toEntity(cityDto)));
    }

    @Loggable
    private City save(City city) {
//        if (true) {
//            throw new CityUniqueConstraintPlateException("eee");
//        }
        try {
            return repo.save(city);
        } catch (DataIntegrityViolationException e) {
            throw new CityUniqueConstraintPlateException(String.format("City plate code already using; %s", city.getPlateCode()));
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    @Override
    @Loggable
    public CityDto update(CityDto cityDto) {
        City city = findCityById(cityDto.getId());
        city.setName(cityDto.getName());
        city.setPlateCode(cityDto.getPlateCode());
        return toDto(save(city));
    }

    @Override
    @Loggable
    public CityDto updatePartial(CityDto cityDto) {
        City city = findCityById(cityDto.getId());
        if (Objects.nonNull(Objects.nonNull(cityDto.getName()) &&
                !Objects.equals(cityDto.getName(), city.getName()))) {
            city.setName(cityDto.getName());
        }
        if (Objects.nonNull(cityDto.getPlateCode()) &&
                !Objects.equals(cityDto.getPlateCode(), city.getPlateCode())) {
            city.setPlateCode(cityDto.getPlateCode());
        }
        return toDto(save(city));
    }

    @Override
    public List<CityDistrictProjection> findAllWithDistricts() {
        return repo.findAllWithDistrictsProjectedBy(CityDistrictProjection.class);
    }

    @Loggable
    private CityDto toDto(City city) {
        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .plateCode(city.getPlateCode())
                .build();
    }

    @Loggable
    private City toEntity(CityDto dto) {
        return City.builder()
                .id(dto.getId())
                .name(dto.getName())
                .plateCode(dto.getPlateCode())
                .build();
    }


}
