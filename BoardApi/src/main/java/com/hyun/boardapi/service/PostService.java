package com.hyun.boardapi.service;

import com.hyun.boardapi.dto.PostRequestDto;
import com.hyun.boardapi.dto.PostResponseDto;
import com.hyun.boardapi.dto.ResponseDto;
import com.hyun.boardapi.entity.Post;
import com.hyun.boardapi.entity.User;
import com.hyun.boardapi.jwt.JwtUtil;
import com.hyun.boardapi.repository.PostRepositrory;
import com.hyun.boardapi.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepositrory postRepositrory;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public PostService(PostRepositrory postRepositrory, JwtUtil jwtUtil, UserRepository userRepository) {
        this.postRepositrory = postRepositrory;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    /*---- 게시물 생성 ----*/
    @Transactional
    public ResponseEntity savePost(PostRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는지 확인
        if (token == null) return new ResponseEntity<>("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

        // 토큰이 있다면 올바른 토큰인지 확인
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return new ResponseEntity<>("잘못된 토큰입니다.", HttpStatus.BAD_REQUEST);
        }

        // 토큰에 있는 유저정보가 존재하는지 확인, 존재한다면 User객체로 만들어준다.
        Optional<User> user = userRepository.findByUsername(claims.getSubject());
        if (user.isEmpty()) return new ResponseEntity<String>("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        User selectedUser = user.get();

        // 입력된 포스트 저장
        Post post = new Post(requestDto.getTitle(), requestDto.getContents(), selectedUser);
        postRepositrory.save(post);

        // 결과 반환
        ResponseDto response = new ResponseDto("게시물 등록 성공", new PostResponseDto(post.getId(), post.getUser().getUsername(), post.getTitle(), post.getContents(), post.getCreatedAt(), post.getModifiedAt()));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /*---- 모든 게시물 조회 ----*/
    @Transactional(readOnly = true)
    public HttpEntity getPosts() {
//        PostListResponseDto postListResponseDto = new PostListResponseDto();

        List<Post> posts = postRepositrory.findAll();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            postList.add(new PostResponseDto(post.getId(), post.getUser().getUsername(), post.getTitle(), post.getContents(), post.getCreatedAt(), post.getModifiedAt()));
        }
        ResponseDto response = new ResponseDto("게시물 리스트 조회 성공", postList);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /*---- 단일 게시물 조회 ----*/
    @Transactional(readOnly = true)
    public HttpEntity getPost(Long id) {
        // 입력된 id의 게시물 조회
        Post post = checkPostExist(id);

        // 결과 반환
        ResponseDto response = new ResponseDto("조회 성공", new PostResponseDto(post.getId(), post.getUser().getUsername(), post.getTitle(), post.getContents(), post.getCreatedAt(), post.getModifiedAt()));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /*---- 게시물 수정 ----*/
    @Transactional
    public HttpEntity updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는지 확인
        if (token == null) return new ResponseEntity<>("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

        // 토큰이 있다면 올바른 토큰인지 확인
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return new ResponseEntity<>("잘못된 토큰입니다.", HttpStatus.BAD_REQUEST);
        }

        // 토큰에 있는 유저정보가 존재하는지 확인, 존재한다면 User객체로 만들어준다.
        Optional<User> user = userRepository.findByUsername(claims.getSubject());
        if (user.isEmpty()) return new ResponseEntity<String>("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        User selectedUser = user.get();

        // 입력된 id를 통한 게시물 가져오기
        Post post = checkPostExist(id);

        // 수정 요청을 한 회원이 게시물을 쓴 회원과 동일한 회원인지 확인
        if (post.getUser().getUsername() != selectedUser.getUsername()) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 선택된 게시물 내용 수정
        post.update(requestDto.getTitle(), requestDto.getContents());

        // 결과 반환 ->
        ResponseDto response = new ResponseDto("수정 성공", new PostResponseDto(post.getId(), post.getUser().getUsername(), post.getTitle(), post.getContents(), post.getCreatedAt(), post.getModifiedAt()));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /*---- 게시물 삭제 ----*/
    @Transactional
    public ResponseEntity deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는지 확인
        if (token == null) return new ResponseEntity<>("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

        // 토큰이 있다면 올바른 토큰인지 확인
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return new ResponseEntity<>("잘못된 토큰입니다.", HttpStatus.BAD_REQUEST);
        }

        // 토큰에 있는 유저정보가 존재하는지 확인, 존재한다면 User객체로 만들어준다.
        Optional<User> user = userRepository.findByUsername(claims.getSubject());
        if (user.isEmpty()) return new ResponseEntity<String>("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        User selectedUser = user.get();

        // 입력된 id를 통한 게시물 가져오기
        Post post = checkPostExist(id);

        // 삭제 요청을 한 회원이 게시물을 쓴 회원과 동일한 회원인지 확인
        if (post.getUser().getUsername() != selectedUser.getUsername()) {
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 입력된 id의 게시물을 삭제
        postRepositrory.delete(post);

        // 결과 반환
        return new ResponseEntity<String>("게시물 삭제 성공", HttpStatus.OK);
    }

    private Post checkPostExist(Long id) {
        return postRepositrory.findById(id).orElseThrow(
                () -> new RuntimeException("게시물을 찾을 수 없습니다.")
        );
    }
}