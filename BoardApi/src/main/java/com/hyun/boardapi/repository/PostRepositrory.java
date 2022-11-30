package com.hyun.boardapi.repository;

import com.hyun.boardapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositrory extends JpaRepository<Post, Long> {
}
