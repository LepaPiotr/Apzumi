package com.apzumi.apzumi.services;

import com.apzumi.apzumi.entity.Post;
import com.apzumi.apzumi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        if(posts != null) {
            for (Post p : posts) {
                postRepository.save(p);
            }
        }
    }

    public void addUpdatePostsSchedule(){
        ResponseEntity<Post[]> responseEntity =
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", Post[].class);
        Post[] posts = responseEntity.getBody();
        if(posts != null)
            for(Post p: posts){
                try {
                    Post byId = postRepository.getById(p.getId());
                    if (byId.getId() > 0 && !byId.isWasEditedByUser())
                        postRepository.save(p);
                }catch (Exception ignored){
                }
            }
    }


    public Post modPost(Post post){
        Post byId = postRepository.getById(post.getId());
        return postRepository.save(new Post(post.getId(), byId.getUserId(), post.getTitle(), post.getBody(), true));
    }

    public List<Post> getPosts (boolean userId, boolean sorted){
        if(userId && sorted){
           return postRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        }
        else if(!userId && sorted){
            return postRepository.findWithoutUserOrderByTitle();
        }
        else if(userId && !sorted){
            return postRepository.findAll();
        }
        else {
            return postRepository.findWithoutUser();
        }
    }
}
