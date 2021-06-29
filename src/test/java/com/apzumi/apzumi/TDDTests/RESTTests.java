package com.apzumi.apzumi.TDDTests;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class RESTTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PostsRepository postsRepository;

    @Test
    @Order(1)
    public void getPosts() throws Exception {
        postsRepository.save(new Post(1, 1, "Title", "Body"));
        mvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.is("Title")));
    }

    @Test
    @Order(2)
    public void deletePosts() throws Exception {
        Post post = postsRepository.findById(1);
        post = new Post( post.getId(), post.getUserId(), "ChangedTitle" ,  "ChangedBody");
        postsRepository.save(post);
        post = postsRepository.findById(1);
        if(post == null || post.getTitle() != "ChangedTitle" || post.getBody() != "ChangedBody")
            assert false;
    }

    @Test
    @Order(3)
    public void deletePosts() throws Exception {
        postsRepository.delete(1);
        Post post = postsRepository.findById(1);
        assert post != null && post.getId() >= 1;
    }
}
