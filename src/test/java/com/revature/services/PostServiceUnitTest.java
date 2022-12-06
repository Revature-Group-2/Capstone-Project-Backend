package com.revature.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

@ExtendWith(SpringExtension.class)
public class PostServiceUnitTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    PostService postService;

    @Test
    public void getAllCallsRepositoryFindAll(){
        when(postRepository.findAll()).thenReturn(new ArrayList<Post>());
        postService.getAll();
        verify(postRepository).findAll();
    }

    @Test
    public void upsertCallsRepositorySave(){
        Post post = mock(Post.class);
        when(postRepository.save(post)).thenReturn(post);
    }

}
