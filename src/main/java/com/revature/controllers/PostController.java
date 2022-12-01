package com.revature.controllers;

import java.util.List;

import com.revature.utils.ProfanityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.services.PostService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
public class PostController {

	private final PostService postService;

    private final ProfanityFilter profanityFilter;

    public PostController(PostService postService, ProfanityFilter profanityFilter) {
        this.postService = postService;
        this.profanityFilter = profanityFilter;
    }
    
    @Authorized
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAll());
    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<?> upsertPost(@RequestBody Post post) {
        if (profanityFilter.hasProfanity(post.getText())){
            return ResponseEntity.badRequest().body("Profanity not allowed!");
        }
    	return ResponseEntity.ok(this.postService.upsert(post));
    }
}
