package com.apzumi.apzumi.services;

import com.apzumi.apzumi.entity.Post;
import com.apzumi.apzumi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PostRepository postRepository;


    public void addUpdatePosts(){
        ResponseEntity<Post[]> responseEntity =
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", Post[].class);
        Post[] posts = responseEntity.getBody();
        if(posts != null)
            for(Post p: posts){
                postRepository.save(p);
            }
    }

    public Post getPostToUpdate(Post post){
        return (new Post(post.getId(), post.getUserId(), post.getTitle(), post.getBody(), true));
    }


}
