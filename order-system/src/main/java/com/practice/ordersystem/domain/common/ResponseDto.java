package com.practice.ordersystem.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseDto {
    private HttpStatus status;
    private String message;
    private Object result;
}
