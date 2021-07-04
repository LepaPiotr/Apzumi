package com.apzumi.apzumi.TDDTests;

import com.apzumi.apzumi.entity.Post;
import com.apzumi.apzumi.repository.PostRepository;
import com.apzumi.apzumi.services.PostService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    public void getPosts() throws Exception {
        System.out.println("robie get 1");
        postRepository.save(new Post(1, 1, "Title", "Body", false));
        mvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.is("Title")));
    }

    @Test
    @Order(2)
    public void modPosts() {
        System.out.println("robie mod 2");
        Optional<Post> postOpt = postRepository.findById(1L);
        Post post = new Post( postOpt.get().getId(), postOpt.get().getUserId(), "ChangedTitle" ,  "ChangedBody", true);
        postRepository.save(post);
        postOpt = postRepository.findById(1L);
        if(!postOpt.isPresent() || !postOpt.get().getTitle().equals("ChangedTitle") || !postOpt.get().getBody().equals("ChangedBody"))
            assert false;
    }

    @Test
    @Order(3)
    public void deletePosts() {
        System.out.println("robie del 3");
        postRepository.deleteById(1L);
        Optional<Post> post = postRepository.findById(1L);
        if(post.isPresent())
        assert false;
    }

    @Test
    @Order(4)
    public void getPostsFromAPI() {
        postService.addUpdatePosts();
        List<Post> all = postRepository.findAll();
        if(all.isEmpty()){
            assert false;
        }
    }
}
