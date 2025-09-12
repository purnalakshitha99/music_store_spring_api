package com.musicstore.musicstore.exception;

import com.musicstore.musicstore.dto.response.error.CustomErrorResponse;
import com.musicstore.musicstore.dto.response.error.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AppControllerAdviser {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public CustomErrorResponse handleNFException(Exception exception) {

        CustomErrorResponse customErrorResponse = new CustomErrorResponse();

        customErrorResponse.setMessage(exception.getMessage());

        return customErrorResponse;

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {

        ErrorResponseDto errorResponse = new ErrorResponseDto();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
        errorResponse.setError("Unauthorized");
        errorResponse.setMessage("Invalid credentials. Please check your email and password."); // Custom message
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}