package com.gun.address.repositories;

import com.gun.address.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Integer> {
    List<City> findAll();
    Page<City> findAll(Pageable pageable);

    @Query("select c from City c where upper(c.name) like upper(concat('%', :name, '%'))")
    List<City> findAllByNameLikeIgnoreCase(String name);

    List<City> findAllByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths={"districts"})
    <T> List<T> findAllWithDistrictsProjectedBy(Class<T> projectionClass);

    <T> List<T> findAllProjectedBy(Class<T> projectionClass);

}
