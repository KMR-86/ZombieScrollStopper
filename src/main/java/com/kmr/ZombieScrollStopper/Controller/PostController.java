package com.kmr.ZombieScrollStopper.Controller;

import com.kmr.ZombieScrollStopper.Model.Post;
import com.kmr.ZombieScrollStopper.Service.OllamaPostGeneratorService;
import com.kmr.ZombieScrollStopper.Service.PostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final OllamaPostGeneratorService ollamaPostGeneratorService;

    public PostController(
            PostService postService,
            OllamaPostGeneratorService ollamaPostGeneratorService
    )
    {
        this.postService = postService;
        this.ollamaPostGeneratorService = ollamaPostGeneratorService;
    }

    @GetMapping("/getPost")
    public List<Post> getPost() {
        return postService.getPost();
    }

    @GetMapping("/getPostById/{id}")
    public Post getPostById(@PathVariable("id")  Long id) {
        return postService.getPostById(id);
    }

    @PostMapping("/savePost")
    public void savePost(@RequestBody Post post) {
        postService.savePost(post);
    }

    @PutMapping("updatePost/{id}")
    public Post updatePost(@RequestBody Post post, @PathVariable("id")  Long id) {
        return postService.updatePost(post, id);
    }

    @DeleteMapping("deletePost/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePostById(id);
    }

    @GetMapping("/generatePost")
    public String generatePost() {
        return ollamaPostGeneratorService.queryOllama("Netherlands");
    }

}
