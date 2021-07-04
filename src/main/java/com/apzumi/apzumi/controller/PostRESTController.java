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


    @GetMapping("/{isUserId}/{isSorted}")
    public List<Post> findAll(@PathVariable("isUserId") boolean userId, @PathVariable("isSorted") boolean sorted){
//        postService.getPosts(userId, sorted);
       return postRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id){
        postRepository.deleteById(id);
    }

    @PostMapping
    private Post modPost(@RequestBody Post post){
        return postRepository.save(postService.getPostToUpdate(post));
    }

    @RequestMapping("/getPosts")
    public void getPost(){
        postService.addUpdatePosts();
    }

}
