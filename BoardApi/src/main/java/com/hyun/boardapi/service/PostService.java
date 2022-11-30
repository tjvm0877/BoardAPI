package com.hyun.boardapi.service;

import com.hyun.boardapi.dto.PostListResponseDto;
import com.hyun.boardapi.dto.PostRequestDto;
import com.hyun.boardapi.dto.PostResponseDto;
import com.hyun.boardapi.dto.ResponseDto;
import com.hyun.boardapi.entity.Post;
import com.hyun.boardapi.repository.PostRepositrory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepositrory postRepositrory;

    /*---- 게시물 생성 ----*/
    @Transactional
    public ResponseDto savePost(PostRequestDto requestDto) {
        Post post = new Post();
        post.fromDto(requestDto);
        postRepositrory.save(post);
        return new ResponseDto("게시물 등록 성공", HttpStatus.OK.value());
    }

    /*---- 모든 게시물 조회 ----*/
    @Transactional(readOnly = true)
    public PostListResponseDto getPosts() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();

        List<Post> posts = postRepositrory.findAll();
        for (Post post : posts) {
            postListResponseDto.addPost(new PostResponseDto(post));
        }
        return postListResponseDto;
    }

    /*---- 단일 게시물 조회 ----*/
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        //NotFoundException
        Post post = chackPostExist(id);
        return new PostResponseDto(post);
    }

    /*---- 게시물 수정 ----*/
    @Transactional
    public ResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = chackPostExist(id);

        // 비밀번호 검증
        if (!post.passwordMatch(requestDto.getPassword())) {
            return new ResponseDto("비밀번호가 틀렸습니다", HttpStatus.OK.value());
        }
        post.update(requestDto);
        return new ResponseDto("게시물 수정 성공", HttpStatus.OK.value());
    }

    /*---- 게시물 삭제 ----*/
    @Transactional
    public ResponseDto deletePost(Long id, String password) {
        Post post = chackPostExist(id);

        if (!post.passwordMatch(password)) {
            return new ResponseDto("비밀번호가 틀렸습니다.", HttpStatus.OK.value());
        }
        postRepositrory.delete(post);
        return new ResponseDto("게시물 삭제 성공", HttpStatus.OK.value());
    }

    private Post chackPostExist(Long id) {
        return postRepositrory.findById(id).orElseThrow(
                () -> new RuntimeException("게시물을 찾을 수 없습니다.")
        );
    }
}