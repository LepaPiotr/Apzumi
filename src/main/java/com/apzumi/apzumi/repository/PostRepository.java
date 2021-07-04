package com.apzumi.apzumi.repository;

import com.apzumi.apzumi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    void deleteById(Long id);
    @Query("SELECT new com.apzumi.apzumi.entity.Post(p.id, p.title, p.body, p.wasEditedByUser) "
            + "FROM Post AS p")
    List<Post> findWithoutUser();
    @Query("SELECT new com.apzumi.apzumi.entity.Post(p.id, p.title, p.body, p.wasEditedByUser) "
            + "FROM Post AS p Order by title")
    List<Post> findWithoutUserOrderByTitle();
}
