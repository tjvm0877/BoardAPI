package com.hyun.boardapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users") // h2의 2.0 버전에는 user를 사용할 수 없기 때문에 users로 바꿔준다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRoleEnum.USER;
    }
}
