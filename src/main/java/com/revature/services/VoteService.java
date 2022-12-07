package com.revature.services;

import com.revature.exceptions.PostNotFoundException;
import com.revature.exceptions.VoteNotFoundException;
import com.revature.models.Post;
import com.revature.models.Vote;
import com.revature.repositories.PostRepository;
import com.revature.repositories.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.revature.models.VoteType.DOWNVOTE;
import static com.revature.models.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;



    @Transactional
    public void vote(Vote vote) throws PostNotFoundException {
        Post post = postRepository.findById(vote.getPost().getId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + vote.getPost().getId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, vote.getUser());
        System.out.println(voteByPostAndUser.isPresent());
        if (voteByPostAndUser.isPresent()){
            System.out.println(voteByPostAndUser.get());
        }

        // code block to check if the user has already voted for the post
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(vote.getVoteType())) {
            if (voteByPostAndUser.get().getVoteType().equals(UPVOTE)){
                voteRepository.deleteById(voteByPostAndUser.get().getVoteId());
                post.setVoteCount(post.getVoteCount() - 1);
                postRepository.save(post);
                return;
            } else {
                voteRepository.deleteById(voteByPostAndUser.get().getVoteId());
                post.setVoteCount(post.getVoteCount() + 1);
                postRepository.save(post);
                return;
            }
        }

        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(UPVOTE) && vote.getVoteType().equals(DOWNVOTE))  {
            post.setVoteCount(post.getVoteCount() - 2);
            voteRepository.deleteById(voteByPostAndUser.get().getVoteId());
        }

        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(DOWNVOTE) && vote.getVoteType().equals(UPVOTE))  {
            post.setVoteCount(post.getVoteCount() + 2);
            voteRepository.deleteById(voteByPostAndUser.get().getVoteId());
        }

        if (!voteByPostAndUser.isPresent()) {
            if (UPVOTE.equals(vote.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 1);
            } else {
                post.setVoteCount(post.getVoteCount() - 1);
            }
        }

        voteRepository.save(mapToVote(vote, post));
        postRepository.save(post);
    }

    private Vote mapToVote(Vote vote, Post post) {
        return Vote.builder()
                .voteType(vote.getVoteType())
                .post(post)
                .user(vote.getUser())
                .build();
    }




}
