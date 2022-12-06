package com.hyun.boardapi.entity;

import com.hyun.boardapi.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends TimeStamps{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    public void fromDto(CreatePost createPost) {
        this.writer = createPost.getWriter();
        this.title = createPost.getTitle();
        this.contents = createPost.getContents();
        this.password = createPost.getPassword();
    }

    public void update(UpdatePost updatePost) {
        this.title = updatePost.getTitle();
        this.contents = updatePost.getContents();
    }

    public boolean passwordMatch(String password) {
        return this.password == password;
    }
}
