package com.kmr.ZombieScrollStopper.Service;

import com.kmr.ZombieScrollStopper.Model.Post;
import com.kmr.ZombieScrollStopper.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public List<Post> getPost() {
        return postRepo.findAll();
    }

    public Post getPostById(Long id) {
        return postRepo.findById(id).get();
    }

    public void savePost(Post post) {
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postRepo.save(post);
    }

    public Post updatePost(Post post, Long id) {
        Post post1 = postRepo.findById(id).get();
        post1.setBody(post.getBody());
        post1.setTitle(post.getTitle());
        post1.setTopic(post.getTopic());
        post1.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return postRepo.save(post1);
    }

    public void deletePostById(Long id) {
        postRepo.deleteById(id);
    }
}
