package com.hyun.boardapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private String msg;
    private int StatusCode;

    public ResponseDto(String msg, int statusCode) {
        this.msg = msg;
        StatusCode = statusCode;
    }
}
