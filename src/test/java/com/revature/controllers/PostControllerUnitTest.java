package com.revature.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.models.Post;
import com.revature.services.PostService;

@WebMvcTest(PostController.class)
public class PostControllerUnitTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    String jsonMockPut;
    
    @BeforeEach
    public void setupMockPutJson(){
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"id\":0,");
        jsonBuilder.append("\"text\":\"\",");
        jsonBuilder.append("\"imageUrl\":\"\",");
        jsonBuilder.append("\"comments\":[],");
        jsonBuilder.append("\"author\":");
            jsonBuilder.append("{\"id\":0,");
            jsonBuilder.append("\"email\":\"\",");
            jsonBuilder.append("\"password\":\"\",");
            jsonBuilder.append("\"firstName\":\"\",");
            jsonBuilder.append("\"lastName\":\"\"");
            jsonBuilder.append("}}");
            jsonMockPut = jsonBuilder.toString();
    }

    @Test
    public void getPostCallsPostServiceGetAll() {
        try {
            this.mockMvc.perform(get("/post"));
        }
        catch (Exception e) {}
        verify(postService).getAll();
    }
    
    @Test
    public void getPostStatusOK() {
        try {
            this.mockMvc.perform(get("/post"))
            .andExpect(status().isOk());
        }
        catch (Exception e) {}
    }

    @Test
    public void PutPostCallsPostServiceUpsert() {

        try {
            this.mockMvc.perform(
                put("/post")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonMockPut.toString()));
        }
        catch (Exception e) {}
        verify(postService).upsert(any(Post.class));
    }

    @Test
    public void putPostStatusOK() {

        try {
            this.mockMvc.perform(
                put("/post")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonMockPut.toString()))
                    .andExpect(status().isOk());
                
        }
        catch (Exception e) {}
    }

    @Test
    public void badPutPostStatusBad() {

        try {
            this.mockMvc.perform(
                put("/post")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("bad json"))
                    .andExpect(status().isBadRequest());
                
        }
        catch (Exception e) {}
    }
}
