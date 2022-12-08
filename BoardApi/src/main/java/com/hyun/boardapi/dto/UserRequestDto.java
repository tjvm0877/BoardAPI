package com.hyun.boardapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;

    public UserRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
