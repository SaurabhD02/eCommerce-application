package com.flipkart.apigateway.exception.handler;

import com.flipkart.apigateway.dto.CustomError;
import com.flipkart.apigateway.exception.UnAuthorizedUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(UnAuthorizedUserException.class)
    public ResponseEntity<?> handleUserNotFoundException(final UnAuthorizedUserException ex) {
        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .header(CustomError.Header.AUTH_ERROR.getName())
                .timeStamp(String.valueOf(new Timestamp(new Date().getTime())))
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(final Exception ex) {
        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(CustomError.Header.INTERNAL_SERVER_ERROR.getName())
                .timeStamp(String.valueOf(new Timestamp(new Date().getTime())))
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
