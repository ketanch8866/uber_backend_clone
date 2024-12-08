package com.lirmo.uber.uberApp.advices;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;
    private int statusCode;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data, HttpStatus status) {
        this();
        this.statusCode = status.value();
        this.data = data;
    }

    public ApiResponse(ApiError error, HttpStatus status) {
        this();
        this.statusCode = status.value();
        this.error = error;
    }
}
