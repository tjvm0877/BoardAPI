package com.hyun.boardproject.service;

import com.hyun.boardproject.dto.PostRequestDto;
import com.hyun.boardproject.dto.PostResponseDto;
import com.hyun.boardproject.entity.Post;
import com.hyun.boardproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> responses = posts.stream()
                .map(m -> new PostResponseDto(m.getId(), m.getWriter(), m.getTitle(), m.getContents(), m.getCreatedAt(), m.getModifiedAt()))
                .collect(Collectors.toList());
        return responses;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        PostResponseDto response = new PostResponseDto(post.getId(), post.getWriter(), post.getTitle(), post.getContents(), post.getCreatedAt(), post.getModifiedAt());
        return response;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto.getWriter(), requestDto.getTitle(), requestDto.getTitle(), requestDto.getPassword());
        postRepository.save(post);

        PostResponseDto responseDto = new PostResponseDto(post.getId(), post.getWriter(), post.getContents(), post.getTitle(), post.getCreatedAt(), post.getModifiedAt());
        return responseDto;
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post selectedPost = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        if (!selectedPost.pwMatch(requestDto.getPassword())) {
            new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        selectedPost.update(requestDto.getTitle(), requestDto.getContent());
        return selectedPost.getId();
    }

    @Transactional
    public void deletePost(Long id, String password) {
        Post selectedPost = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        //비밀번호 처리 필요
        if (!selectedPost.pwMatch(password)) {
            new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        // 게시물 삭제
        postRepository.deleteById(id);
    }
}
