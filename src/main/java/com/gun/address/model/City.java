package com.gun.address.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cities",
        indexes = {@Index(name = "unique_idx_plate_code", columnList = "plate_code", unique = true)})
public class City extends NamedBaseEntity {
    @Column(name = "plate_code")
    private String plateCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city", cascade = CascadeType.REMOVE)
    public List<District> districts;

    public City() {
    }

    @Builder
    public City(Integer id, String name, String plateCode) {
        super(id, name);
        this.plateCode = plateCode;
    }

    @Override
    public String toString() {
        return "City{" +
                super.toString() +
                ", plateCode='" + plateCode + '\'' +
                '}';
    }
}
