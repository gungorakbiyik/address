package com.gun.address.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDto implements Serializable {
    private Integer id;
    @NotEmpty()
    @Size(min = 2, max = 255)
    private String name;
    @NotEmpty()
    @Size(min = 2, max = 5)
    private String plateCode;
}
