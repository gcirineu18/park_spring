package com.aluufc.demoparkingapi.web.exception;

import com.aluufc.demoparkingapi.exception.EntityNotFoundException;
import com.aluufc.demoparkingapi.exception.PasswordInvalidException;
import com.aluufc.demoparkingapi.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
Es ist speziell für Behandlung von Ausnahmen entwicklelt. Ermöglicht es,
Ausnahmen zentral zu behandeln, anstatt sie in jedem Controller einzeln zu
behandeln
* */
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    /* Diese Annotation gibt an, welche Art von Ausnahme behandelt werden soll
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                        HttpServletRequest request, BindingResult result){

        log.error("API Error - ", exception);
       return ResponseEntity
               .status(HttpStatus.UNPROCESSABLE_ENTITY) // Status code 422
               .contentType(MediaType.APPLICATION_JSON)
               .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) inválido(s)", result));
    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> methodUsernameUniqueViolationException(RuntimeException exception,
                                                                        HttpServletRequest request){

        log.error("API Error - ", exception);
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // Status code 409
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, exception.getMessage()));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException exception,
                                                                               HttpServletRequest request){

        log.error("API Error - ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // Status code 404
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> PasswordInvalidException(RuntimeException exception,
                                                                HttpServletRequest request){

        log.error("API Error - ", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // Status code 400
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
}
