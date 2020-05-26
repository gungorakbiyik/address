package com.gun.address.exception;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ExceptionResponse implements Serializable {
    private String message;
    private Date systemDate;
}
