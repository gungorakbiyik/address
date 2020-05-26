package com.gun.address.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "districts")
public class District extends NamedBaseEntity {
    @ManyToOne
    private City city;

    public District() {
    }

    @Builder
    public District(Integer id, String name, City city) {
        super(id, name);
        this.city = city;
    }
}
