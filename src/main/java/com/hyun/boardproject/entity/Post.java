package com.hyun.boardproject.entity;

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
    private String contents;

    // 게시물 변경 시 필요한 비밀번호
    @Column(nullable = false)
    private String password;

    public Post(String writer, String title, String contents, String password) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.password = password;
    }
    //    public void create(String writer, String title, String contents, String password) {
//        this.writer = writer;
//        this.title = title;
//        this.contents = contents;
//        this.password = password;
//    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean pwMatch(String enteredPw) {
        if (enteredPw.equals(this.password)) return true;
        return false;
    }
}
