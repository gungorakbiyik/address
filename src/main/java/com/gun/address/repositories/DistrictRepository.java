package com.gun.address.repositories;

import com.gun.address.model.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends CrudRepository<District, Integer> {
    List<District> findAllByCity_Id(Integer cityId);
    Page<District> findAllByCity_Id(Integer cityId, Pageable pageable);
    Optional<District> findByIdAndAndCity_Id(Integer id, Integer cityId);

    List<District> findAllByCity_IdAndNameContainingIgnoreCase(Integer cityId, String name);
}
