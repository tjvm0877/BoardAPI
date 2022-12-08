package com.hyun.boardapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto<T> {
    private String msg;
    private T data;

    public ResponseDto(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }
}
