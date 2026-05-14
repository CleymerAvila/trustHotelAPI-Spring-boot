package edu.unicolombo.trustHotelAPI.infrastructure.errors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record ErrorDto(
        LocalDateTime timestamp,
        int status, String error,
        String message , String path,
        Map<String, String> errors) {


    public ErrorDto(int status, String error, String message, String path){
        this(LocalDateTime.now(), status, error, message, path, new HashMap<>());
    }

    void setErrors(Map<String, String> errores){
        this.errors.putAll(errores);
    }
}
