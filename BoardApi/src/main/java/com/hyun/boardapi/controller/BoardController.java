package com.hyun.boardapi.controller;

import com.hyun.boardapi.dto.PostListResponseDto;
import com.hyun.boardapi.dto.PostRequestDto;
import com.hyun.boardapi.dto.PostResponseDto;
import com.hyun.boardapi.dto.ResponseDto;
import com.hyun.boardapi.repository.PostRepositrory;
import com.hyun.boardapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping("/post")
    public ResponseDto savePost(@RequestBody PostRequestDto requestDto) {
        return postService.savePost(requestDto);
    }

    // 게시물 조회 (하나만)
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시물 조회 (전부)
    @GetMapping("/post")
    public PostListResponseDto getPosts() {
        return postService.getPosts();
    }

    // 게시물 수정 (비밀번호 필요)
    @PutMapping("/post/{id}")
    public ResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // 게시물 삭제
    @DeleteMapping("/post/{id}")
    public ResponseDto deletePost(@PathVariable Long id, @RequestBody Map<String, String> passwordData) {
        return postService.deletePost(id, passwordData.get("password"));
    }
}
