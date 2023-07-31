package com.example.controller;

import com.example.model.dto.CreateIssueRequestDto;
import com.example.model.dto.ProjectDto;
import com.example.model.dto.UpdateIssueRequestDto;
import com.example.model.dto.PersonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(value = {"classpath:db/test_script.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class IssueControllerTest {


    public static final String ID_FOR_TEST_ISSUE = "1000";
    PersonDto person;
    ProjectDto project;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        person = new PersonDto();
        person.setId(1L);
        person.setName("Mike");
        project = new ProjectDto();
        project.setId(1L);
        project.setName("Project 1");
    }

    @Test
    void shouldReturnAllIssues() throws Exception {
        mockMvc.perform(get("/issues"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.totalElements", greaterThan(1)));
        ;
    }

    @Test
    void shouldReturnOneIssue() throws Exception {
        mockMvc.perform(get("/issues/" + ID_FOR_TEST_ISSUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1000)))
                .andExpect(jsonPath("$.description", equalTo("Issue for test")))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    void shouldPostNewIssueAndOk() throws Exception {
        var issue = new CreateIssueRequestDto();
        issue.setSubject("Test subject");
        issue.setDescription("Test description");
        issue.setPerson(person);
        issue.setProject(project);
        mockMvc.perform(post("/issues")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(issue)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
//    VALUES(1000, 'Issue', 'Issue for test', 3, 1, 2, now(), now());
    void shouldPutExistingIssueAndOk() throws Exception {
        var issue = new UpdateIssueRequestDto();
        issue.setId(1000L);
        issue.setSubject("Issue test");
        issue.setDescription("Issue for test updated");
        issue.setProject(project);
        issue.setPerson(person);
        mockMvc.perform(put("/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(issue)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.subject", equalTo("Issue test")))
                .andExpect(jsonPath("$.description", equalTo("Issue for test updated")));
    }
}
