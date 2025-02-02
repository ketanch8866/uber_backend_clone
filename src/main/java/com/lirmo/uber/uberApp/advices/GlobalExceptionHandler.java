package com.lirmo.uber.uberApp.advices;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lirmo.uber.uberApp.exceptions.DriverNotAvailableException;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.exceptions.RideRequestCanNotAcceptedException;
import com.lirmo.uber.uberApp.exceptions.RuntimeConflictException;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(JwtException.class)
    // public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException
    // exception) {
    // ApiError error = ApiError.builder()
    // .status(HttpStatus.UNAUTHORIZED)
    // .message(exception.getMessage())
    // .build();

    // return buildApiErrorResponse(error);
    // }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        ApiError error = ApiError.builder().status(HttpStatus.UNAUTHORIZED).message(ex.getLocalizedMessage()).build();
        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJWTException(JwtException ex) {
        ApiError error = ApiError.builder().status(HttpStatus.UNAUTHORIZED).message(ex.getLocalizedMessage()).build();
        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError error = ApiError.builder().status(HttpStatus.FORBIDDEN).message(ex.getLocalizedMessage()).build();
        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourseNotFound(ResourceNotFoundException exception) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();

        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflict(RuntimeConflictException exception) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .build();
        // log.info("handleRuntimeConflict " + error + exception.getMessage());
        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
        ApiError error = ApiError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationError(MethodArgumentNotValidException exception) {

        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed").subError(errors).build();
        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(RideRequestCanNotAcceptedException.class)
    public ResponseEntity<ApiResponse<?>> handleRideRequestCanNotAcceptedException(
            RideRequestCanNotAcceptedException exception) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage()).build();
        return buildApiErrorResponse(error);
    }

    @ExceptionHandler(DriverNotAvailableException.class)
    public ResponseEntity<ApiResponse<?>> handleDriverNotAvailableError(DriverNotAvailableException exception) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage()).build();
        return buildApiErrorResponse(error);
    }

    private ResponseEntity<ApiResponse<?>> buildApiErrorResponse(ApiError error) {
        return new ResponseEntity<>(new ApiResponse<>(error, error.getStatus()), error.getStatus());
    }
}
