package com.revature.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	@Autowired
	private CommentRepository commentRepository;

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	public List<Post> getAllSorted() {
		List<Post> posts = this.postRepository.findAll();
		Collections.sort(posts);
		return posts;
	}

	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}

	public Comment upsertComment(Comment comment) {
		return this.commentRepository.save(comment);
	}

	public List<Post> userPosts(User user) {
		return this.postRepository.findAllByAuthor(user);
	}
}
