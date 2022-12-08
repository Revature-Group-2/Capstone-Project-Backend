package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
	void test() {
		fail("Not yet implemented");
	}

}
