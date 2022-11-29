package com.hyun.boardproject.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String writer;
    private String title;
    private String content;
    private String password;

    public PostRequestDto(String writer, String title, String content, String password) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.password = password;
    }
}
