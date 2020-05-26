package com.gun.address.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NamedBaseEntity extends BaseEntity {
    @Column
    private String name;

    public NamedBaseEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
