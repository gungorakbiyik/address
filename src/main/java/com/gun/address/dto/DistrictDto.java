package com.gun.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto implements Serializable {
    private Integer id;
    @NotEmpty()
    @Size(min = 2, max = 255)
    private String name;
}
