package com.hyun.boardapi.service;

import com.hyun.boardapi.dto.ResponseDto;
import com.hyun.boardapi.dto.UserRequestDto;
import com.hyun.boardapi.entity.User;
import com.hyun.boardapi.jwt.JwtUtil;
import com.hyun.boardapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> signup(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        String idPattern = "^[a-z0-9]{4,10}$";
        String passwordPattern = "^[A-Z0-9a-z\\d~!@#$%^&*()+|=]{8,16}$";

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) { // 중복된 회원이 있을 경우
            return new ResponseEntity<String>("이미 존재하는 사용자 이름입니다.", HttpStatus.BAD_REQUEST);
        }

        User user = new User(username, password);
        userRepository.save(user);

        return new ResponseEntity<String>("회원가입에 성공하셨습니다.", HttpStatus.OK);
    }

    public ResponseEntity<String> login(UserRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 입력한 사용자 정보가 있는지 확인
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) return new ResponseEntity<String>("존재하지 않는 사용자 입니다.", HttpStatus.BAD_REQUEST);

        User selectedUser = user.get();

        // 입력한 비밀번호가 일치하는지 확인
        if(!selectedUser.getPassword().equals(password)){
            return new ResponseEntity<String>("입력하신 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        ResponseEntity responseEntity = new ResponseEntity("로그인에 성공하였습니다.", HttpStatus.OK);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(selectedUser.getUsername(), selectedUser.getRole()));
        return responseEntity;
    }
}
