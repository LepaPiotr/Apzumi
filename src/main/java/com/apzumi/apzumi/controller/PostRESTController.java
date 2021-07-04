package com.apzumi.apzumi.controller;


import com.apzumi.apzumi.entity.Post;
import com.apzumi.apzumi.repository.PostRepository;
import com.apzumi.apzumi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostRESTController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @GetMapping()
    public List<Post> test(){
        return postRepository.findWithoutUserOrderByTitle();
    }

    @GetMapping("/{isUserId}/{isSorted}")
    public List<Post> findAll(@PathVariable("isUserId") boolean userId, @PathVariable("isSorted") boolean sorted){

       return postService.getPosts(userId, sorted);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id){
        postRepository.deleteById(id);
    }

    @PostMapping
    public Post modPost(@RequestBody Post post){
        return postService.modPost(post);
    }

    @RequestMapping("/getPosts")
    public void getPost(){
        postService.addUpdatePosts();
    }

}
