package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.exceptions.PostNotFoundException;
import com.revature.models.Post;
import com.revature.models.Vote;
import com.revature.repositories.PostRepository;
import com.revature.repositories.VoteRepository;

@ExtendWith(SpringExtension.class)
class VoteServiceUnitTest {

	@Mock
	private VoteRepository voteRepository;
	@Mock
	private PostRepository postRepository;
	
	@InjectMocks
	VoteService voteService;
	
	@Test
	@Disabled
	public void votePostNotFound() {
		Vote vote = mock(Vote.class);
		when(postRepository.findById(vote.getPost().getId())).thenReturn(Optional.empty());
		try {
			voteService.vote(vote);
			fail();
		}catch(PostNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}

}
