package com.hyun.boardproject.controller;

import com.hyun.boardproject.dto.PostRequestDto;
import com.hyun.boardproject.dto.PostResponseDto;
import com.hyun.boardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 전체 게시글 목록 조회
    @GetMapping("/board/post")
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // 선택한 개시글 조회
    @GetMapping("/board/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 작성
    @PostMapping("/board/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 선택한 게시글 수정 (비밀번호 일치여부 확인 필요)
    @PutMapping("/board/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // 선택한 게시글 삭제 (비밀번호 일치여부 확인 필요)
    @DeleteMapping("/board/post/{id}/{password}")
    public void deletePost(@PathVariable Long id, @PathVariable String password) {
        postService.deletePost(id, password);
    }
}
