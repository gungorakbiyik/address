package com.gun.address.services.impl;

import com.gun.address.aop.Loggable;
import com.gun.address.dto.DistrictDto;
import com.gun.address.model.City;
import com.gun.address.model.District;
import com.gun.address.repositories.DistrictRepository;
import com.gun.address.services.CityService;
import com.gun.address.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Loggable
public class DistrictServiceImpl implements DistrictService {
    private DistrictRepository repo;
    private CityService cityService;

    @Autowired
    public DistrictServiceImpl(DistrictRepository repo, CityService cityService) {
        this.repo = repo;
        this.cityService = cityService;
    }

    public List<DistrictDto> findByCityId(int cityId) {
        return repo.findAllByCity_Id(cityId)
                .stream()
                .map(district -> toDto(district))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistrictDto> findByCityIdAndNameLike(int cityId, String searchDistrictName) {
        return repo.findAllByCity_IdAndNameContainingIgnoreCase(cityId, searchDistrictName)
                .stream()
                .map(district -> toDto(district))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DistrictDto> findByCityId(int cityId, Pageable pageable) {
        Page<District> districtPage = repo.findAllByCity_Id(cityId, pageable);
        List<DistrictDto> districtDtos = districtPage.get()
                .map(district -> toDto(district))
                .collect(Collectors.toList());
        return new PageImpl<>(districtDtos, pageable, districtPage.getTotalElements());
    }

    @Override
    public DistrictDto findByIdAndCityId(int districtId, int cityId) {
        return toDto(findDistrictByIdAndCityId(districtId, cityId));
    }

    private District findDistrictByIdAndCityId(int districtId, int cityId) {
        return repo.findByIdAndAndCity_Id(districtId, cityId)
                .orElseThrow(() -> new NoSuchElementException(String.format("District does not exists %d", districtId)));
    }

    @Override
    public DistrictDto save(int cityId, DistrictDto districtDto) {
        City city = cityService.findCityById(cityId);
        return toDto(save(toEntity(districtDto, city)));
    }

    private District save(District district) {
        return repo.save(district);
    }

    @Override
    public DistrictDto update(int cityId, DistrictDto districtDto) {
        District district = findDistrictByIdAndCityId(districtDto.getId(), cityId);
        district.setName(districtDto.getName());
        return toDto(save(district));
    }

    @Override
    public DistrictDto updatePartial(int cityId, DistrictDto districtDto) {
        District district = findDistrictByIdAndCityId(districtDto.getId(), cityId);
        if (!Objects.equals(district.getName(), districtDto.getName())) {
            district.setName(districtDto.getName());
        }
        return toDto(save(district));
    }


    private DistrictDto toDto(District district) {
        return DistrictDto.builder()
                .id(district.getId())
                .name(district.getName())
                .build();
    }

    private District toEntity(DistrictDto dto, City city) {
        return District.builder()
                .id(dto.getId())
                .name(dto.getName())
                .city(city)
                .build();
    }
}
