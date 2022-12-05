package com.revature.services;

import com.revature.repositories.PostRepository;
import com.revature.repositories.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;



}
