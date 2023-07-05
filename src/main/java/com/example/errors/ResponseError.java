package com.example.errors;

import lombok.Data;

@Data
public class ResponseError {

    private final String status;
    private final String code;
    private final String error;

}
