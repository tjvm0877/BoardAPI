package com.hyun.boardapi.dto;

import com.hyun.boardapi.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String writer;
    private String title;
    private String contents;

    public PostResponseDto(Post post) {
        this.writer = post.getWriter();
        this.title = post.getTitle();
        this.contents = post.getContents();
    }
}
