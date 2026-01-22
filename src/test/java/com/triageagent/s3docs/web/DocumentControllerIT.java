package com.triageagent.s3docs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.triageagent.s3docs.s3.S3DocumentRepository;
import com.triageagent.s3docs.web.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.triageagent.s3docs.service.DocumentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentControllerIT {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockBean DocumentService service;


    // IMPORTANT: open DocumentController and set this to its base mapping.
    // Example: if you have @RequestMapping("/documents") then keep "/documents".
    private static final String BASE = "/api/v1/docs";

    @Test
    void get_returns_200_and_json_body() throws Exception {
        byte[] bytes = "{\"hello\":\"world\"}".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        when(service.get("abc")).thenReturn(bytes);



        mvc.perform(get(BASE + "/{key}", "abc").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hello").value("world"));
    }

    @Test
    void get_missing_returns_404() throws Exception {
        when(service.get("missing")).thenThrow(new NotFoundException("Document not found: missing"));


        mvc.perform(get(BASE + "/{key}", "missing").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
