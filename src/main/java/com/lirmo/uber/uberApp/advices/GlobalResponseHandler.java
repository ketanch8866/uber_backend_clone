package com.lirmo.uber.uberApp.advices;

import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, @SuppressWarnings("null") MethodParameter returnType,
            @SuppressWarnings("null") MediaType selectedContentType,
            @SuppressWarnings("null") Class<? extends HttpMessageConverter<?>> converterType,
            @SuppressWarnings("null") ServerHttpRequest request,
            @SuppressWarnings("null") ServerHttpResponse response) {

        List<String> allowedRoutes = List.of("/v3/api-docs", "/actuator");
        boolean isAllowed = allowedRoutes.stream().anyMatch(route -> request.getURI().getPath().contains(route));

        if (isAllowed || (body instanceof ApiResponse<?>)) {
            return body;
        }
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    @Override
    public boolean supports(@SuppressWarnings("null") MethodParameter returnType,
            @SuppressWarnings("null") Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}
