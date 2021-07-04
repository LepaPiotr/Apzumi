package com.apzumi.apzumi.repository;

import com.apzumi.apzumi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    public void deleteById(Long id);
}
