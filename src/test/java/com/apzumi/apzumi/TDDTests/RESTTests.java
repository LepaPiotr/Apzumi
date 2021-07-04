package com.apzumi.apzumi.TDDTests;

import com.apzumi.apzumi.entity.Post;
import com.apzumi.apzumi.repository.PostRepository;
import com.apzumi.apzumi.services.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RESTTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    @Order(1)
    public void savePosts() throws Exception {
        postRepository.save(new Post(1, 1, "Title", "Body", false));
        List<Post> all = postRepository.findAll();
        if(all.isEmpty())
            assert false;
    }

    @Test
    @Order(2)
    public void modPosts() {
        Optional<Post> postOpt = postRepository.findById(1L);
        Post post = new Post( postOpt.get().getId(), postOpt.get().getUserId(), "ChangedTitle" ,  "ChangedBody", true);
        postRepository.save(post);
        postOpt = postRepository.findById(1L);
        if(postOpt.isEmpty() || !postOpt.get().getTitle().equals("ChangedTitle") || !postOpt.get().getBody().equals("ChangedBody"))
            assert false;
    }
    @Test
    @Order(3)
    public void modPostsWithoutUserId() {
        Optional<Post> postOpt = postRepository.findById(1L);
        long userId = postOpt.get().getUserId();
        Post post = new Post( postOpt.get().getId(), userId+1, "ChangedTitle" ,  "ChangedBody", true);
        postService.modPost(post);
        postOpt = postRepository.findById(1L);
        if(postOpt.isEmpty() || postOpt.get().getUserId() != userId)
            assert false;
    }

    @Test
    @Order(4)
    public void deletePosts() {
        System.out.println("robie del 3");
        postRepository.deleteById(1L);
        Optional<Post> post = postRepository.findById(1L);
        if(post.isPresent())
        assert false;
    }

    @Test
    @Order(5)
    public void findOrderBy() {
        postRepository.save(new Post(1, 1, "BTitle", "Body", false));
        postRepository.save(new Post(2, 2, "ATitle", "Body", false));
        List<Post> posts = postService.getPosts(true, true);
        if(!posts.get(0).getTitle().equals("ATitle") || posts.get(0).getUserId() < 1)
            assert false;
        posts = postService.getPosts(true, false);
        if(!posts.get(0).getTitle().equals("BTitle") || posts.get(0).getUserId() < 1)
            assert false;
        posts = postService.getPosts(false, false);
        if(!posts.get(0).getTitle().equals("BTitle") || posts.get(0).getUserId() > 0)
            assert false;
        posts = postService.getPosts(false, true);
        if(!posts.get(0).getTitle().equals("ATitle") || posts.get(0).getUserId() > 0)
            assert false;
        postRepository.deleteById(1L);
        postRepository.deleteById(2L);
    }

    @Test
    @Order(6)
    public void getPostsFromAPI() {
        postService.addUpdatePosts();
        List<Post> all = postRepository.findAll();
        if(all.isEmpty()){
            assert false;
        }
    }

    @Test
    @Order(7)
    public void updatePostsFromAPI() {
        List<Post> all = postRepository.findAll();
        int rows = all.size();
        for(int i = 0; i < 10; i++){
            postRepository.delete(all.get(i));
        }
        postService.addUpdatePostsSchedule();

        if(postRepository.findAll().size() != rows - 10)
            assert false;
        Post modPost = new Post(all.get(11).getId(), all.get(11).getUserId(), "ChangetTittle", all.get(11).getBody(), all.get(11).isWasEditedByUser());
            postService.modPost(modPost);

        postService.addUpdatePostsSchedule();

        Optional<Post> byId = postRepository.findById(modPost.getId());
        if(byId.isEmpty() || !byId.get().getTitle().equals("ChangetTittle"))
            assert false;
    }
}
