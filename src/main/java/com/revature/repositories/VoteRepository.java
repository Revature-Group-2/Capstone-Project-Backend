package com.revature.repositories;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.models.Vote;

import java.util.Optional;

public interface VoteRepository {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
