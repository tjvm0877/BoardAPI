package com.hyun.boardproject.entity;

import com.hyun.boardproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends TimeStamps {

    // 게시물 pk
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 작성자 작성자
    @Column(nullable = false)
    private String writer;

    // 게시물 제목
    @Column(nullable = false)
    private String title;

    // 게시물 내용
    @Column(nullable = false)
    private String content;

    // 게시물 변경 시 필요한 비밀번호
    @Column(nullable = false)
    private String password;

    public Post(PostRequestDto postRequestDto) {
        this.writer = postRequestDto.getWriter();
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.password = postRequestDto.getPassword();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
