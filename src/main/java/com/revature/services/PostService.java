package com.revature.services;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;

@Service
public class PostService {

	@Autowired
	private CommentRepository commentRepository;

	private PostRepository postRepository;

	@Autowired
    private ProfileRepository profileRepository;

	@Autowired
	private UserService usersService;

	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	public Optional<Post> getOne(int id) { return this.postRepository.findById(id); }

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

	public void delete(int id) { postRepository.deleteById(id); }

	public List<Post> getAllSubscribedPosts(User sessionUser) throws UserNotFoundException, ProfileNotFoundException {
		List<Post> posts = new LinkedList<>();
        Optional<Profile> profile = profileRepository.findByOwner(sessionUser);

		if (profile.isEmpty())
			throw new ProfileNotFoundException("The profile related to this user has not been found");

		List<Integer> subscriptionIds = profile.get().getSubscriptionIds() != null ? profile.get().getSubscriptionIds() : new LinkedList<>();
		subscriptionIds.add(sessionUser.getId());

		for (int id : subscriptionIds)
			// Add all posts by using user as a key to the posts.
			posts.addAll(postRepository.findAllByAuthor(usersService.findById(id).get()));

		Collections.sort(posts);

		return posts;
	}
}
