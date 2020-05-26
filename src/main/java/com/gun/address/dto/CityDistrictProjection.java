package com.gun.address.dto;

import java.io.Serializable;
import java.util.List;

public interface CityDistrictProjection extends Serializable {

    Integer getId();

    String getName();

    List<DistrictProjection> getDistricts();

    interface DistrictProjection {
        Integer getId();
        String getName();
    }

}
