package com.hyun.boardapi.controller;

import com.hyun.boardapi.dto.PostRequestDto;
import com.hyun.boardapi.dto.ResponseDto;
import com.hyun.boardapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping("/post")
    public ResponseEntity<ResponseDto> savePost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.savePost(requestDto, request);
    }

    // 게시물 조회 (하나만)
    @GetMapping("/post/{id}")
    public HttpEntity getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시물 조회 (전부)
    @GetMapping("/post")
    public HttpEntity getPosts() {
        return postService.getPosts();
    }

    // 게시물 수정 (비밀번호 필요)
    @PutMapping("/post/{id}")
    public HttpEntity updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    // 게시물 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
}
