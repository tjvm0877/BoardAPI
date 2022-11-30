package com.hyun.boardapi.dto;

import com.hyun.boardapi.entity.CreatePost;
import com.hyun.boardapi.entity.UpdatePost;
import lombok.Getter;

@Getter
public class PostRequestDto implements UpdatePost, CreatePost {
    private String writer;
    private String title;
    private String contents;
    private String password;

}
