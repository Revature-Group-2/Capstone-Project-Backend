package com.revature.controllers;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.revature.utils.ProfanityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.services.PostService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
public class PostController {

	private final PostService postService;

    @Autowired
    private UserService userService;

    private final ProfanityFilter profanityFilter;

    public PostController(PostService postService, ProfanityFilter profanityFilter) {
        this.postService = postService;
        this.profanityFilter = profanityFilter;
    }
    
    @Authorized
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAllSorted());
    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<?> upsertPost(@RequestBody Post post) throws IOException {
        if (profanityFilter.hasProfanity(post.getText())){
            return ResponseEntity.badRequest().body("profanity");
        }
    	return ResponseEntity.ok(this.postService.upsert(post));
    }

    @Authorized
    @PutMapping("/comment")
    public ResponseEntity<Comment> upsertComment(@RequestBody Comment comment) {
    	return ResponseEntity.ok(this.postService.upsertComment(comment));
    }


    /**
     * Retrieves all user's posts by <b>user's id</b> specified in the path.
     * @param id
     * @return List<Post> || Status "400"
     */
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<List<Post>> userPosts(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.postService.userPosts(this.userService.findById(id).get()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
