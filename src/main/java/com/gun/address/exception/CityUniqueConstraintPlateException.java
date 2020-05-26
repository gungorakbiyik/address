package com.gun.address.exception;

public class CityUniqueConstraintPlateException extends RuntimeException {
    public CityUniqueConstraintPlateException() {
    }

    public CityUniqueConstraintPlateException(String message) {
        super(message);
    }

    public CityUniqueConstraintPlateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityUniqueConstraintPlateException(Throwable cause) {
        super(cause);
    }

    public CityUniqueConstraintPlateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
