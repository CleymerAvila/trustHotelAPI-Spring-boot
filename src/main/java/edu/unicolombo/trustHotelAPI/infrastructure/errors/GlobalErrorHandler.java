package edu.unicolombo.trustHotelAPI.infrastructure.errors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import edu.unicolombo.trustHotelAPI.infrastructure.errors.exception.BusinessLogicValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> noFoundErrorHandler(
            EntityNotFoundException ex,
            HttpServletRequest request){

        return buildResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(BusinessLogicValidationException.class)
    public ResponseEntity<ErrorDto> businessLogicValidationHandler(
            BusinessLogicValidationException ex,
            HttpServletRequest request){
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> noValidDataHandler(
            MethodArgumentNotValidException e,
            HttpServletRequest request){


        var errors = e.getFieldErrors().stream().map(ErrorValidationData::new).toList();

        Map<String, String> errorMap = new HashMap<>();

        e.getBindingResult().getFieldErrors()
        .forEach(error ->
            errorMap.put(error.getField(), error.getDefaultMessage())
        );

        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        errorDto.setErrors(errorMap);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> dataIntegrityErrorHandler(
            DataIntegrityViolationException e,
            HttpServletRequest request){

        return buildResponse(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<?> handleJwt(
            JWTVerificationException ex,
            HttpServletRequest request) {

        return buildResponse(HttpStatus.UNAUTHORIZED, ex, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsErrorHandler(
            BadCredentialsException exception,
            HttpServletRequest request){

        return buildResponse(HttpStatus.UNAUTHORIZED, exception, request);
    }

    private ResponseEntity<ErrorDto> buildResponse(HttpStatus status, Exception ex, HttpServletRequest request){

        String message = (ex.getMessage() != null && !ex.getMessage().isEmpty())
                ? ex.getMessage()
                : "Unexpected Error Occurred";


        ErrorDto apiErrorDto = new ErrorDto(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(apiErrorDto);
    }

    public record ErrorValidationData(String field, String error){
        public ErrorValidationData(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
