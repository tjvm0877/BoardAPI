package com.hyun.boardapi.entity;

import com.hyun.boardapi.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


//    public void fromDto(PostRequestDto requestDto) {
//        this.writer = requestDto.getWriter();
//        this.title = requestDto.getTitle();
//        this.contents = requestDto.getContents();
//        this.password = requestDto.getPassword();
//    }
//    public void fromDto(String writer, String title, String contents, String password) {
//        this.writer = writer;
//        this.title = title;
//        this.contents = contents;
//        this.password = password;
//    }

    // entity는 항상 위에서 아래를 내려댜봐야되기때문에 DTO의 변경에 영향을 받음 안된다.
    // strategy패턴 일수도....
    // 어느 DTO를 쓰더라도 entity를 변경하지 않아도 된다. => 개방 폐쇄 원칙의 느낌스
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
        if(this.password.equals(password)) return true;
        return false;
    }
}
