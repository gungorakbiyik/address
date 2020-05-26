package com.gun.address.services;

import com.gun.address.dto.DistrictDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DistrictService {

    List<DistrictDto> findByCityId(int cityId);

    Page<DistrictDto> findByCityId(int cityId, Pageable pageable);

    DistrictDto findByIdAndCityId(int districtId, int cityId);

    DistrictDto save(int cityId, DistrictDto districtDto);

    DistrictDto update(int cityId, DistrictDto districtDto);

    DistrictDto updatePartial(int cityId, DistrictDto districtDto);

    List<DistrictDto> findByCityIdAndNameLike(int cityId, String searchDistrictName);
}
