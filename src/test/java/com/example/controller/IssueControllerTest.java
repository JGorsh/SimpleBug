package com.example.controller;

import com.example.model.dto.*;
import com.example.service.FilterSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.example.model.dto.FieldNameFilter.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


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
    @Autowired
    private FilterSpecification filterSpecification;

    private List<Object> list ;

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

    @Test
    void shouldReturnAllIssuesWithCriteria() throws Exception {
        list = new ArrayList<>();
        mockMvc.perform(post("/issues/filters").contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", greaterThan(1)));
    }

    @ParameterizedTest(name = "ParameterizedTest")
    @MethodSource("generateData")
    void shouldReturnIssuesWithParameters(List<SearchIssueDto> issueDtoList, Integer totalElement) throws Exception {
        mockMvc.perform(post("/issues/filters").contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(issueDtoList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(totalElement)));
                //.andExpect(jsonPath("$.content.[0].id", is(Integer.parseInt(issueDtoList.get(0).getValue()))));
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(Arrays.asList(new SearchIssueDto(ID, "1")), 1),
                Arguments.of(Arrays.asList(new SearchIssueDto(DESCRIPTION, "broke")), 1),
                Arguments.of(Arrays.asList(new SearchIssueDto(SUBJECT, "Issue3")), 1)
        );
    }
//    @Test
//    void shouldReturnIssuesWithFilterId() throws Exception {
//        list = new ArrayList<>();
//        var issue = new SearchIssueDto();
//        issue.setKey("id");
//        issue.setValue(1L);
//        issue.setOperation(SearchOperation.EQUAL);
//        list.add(issue);
//        mockMvc.perform(post("/issues/filters").contentType(APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(list)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalElements", is(1)))
//                .andExpect(jsonPath("$.content.[0].id", is(1)));
//    }
//
//    @Test
//    void shouldReturnFourIssuesWithFilterDescription() throws Exception {
//        list = new ArrayList<>();
//        var issue = new SearchIssueDto();
//        issue.setKey("description");
//        issue.setValue("bug");
//        issue.setOperation(SearchOperation.LIKE);
//        list.add(issue);
//        mockMvc.perform(post("/issues/filters")
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(list)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalElements", is(4)));
//    }
//    @Test
//    void shouldReturnOneIssuesWithFilterDescription() throws Exception {
//        list = new ArrayList<>();
//        var issue = new SearchIssueDto();
//        issue.setKey("description");
//        issue.setValue("bro");
//        issue.setOperation(SearchOperation.LIKE);
//        list.add(issue);
//        mockMvc.perform(post("/issues/filters")
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(list)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalElements", is(1)))
//                .andExpect(jsonPath("$.content.[0].id", is(4)))
//                .andExpect(jsonPath("$.content.[0].description", is("broke")));
//    }
//
//    @Test
//    void shouldReturnFourIssuesWithFilterAll() throws Exception {
//        list = new ArrayList<>();
//        var issue1 = new SearchIssueDto("description","bug",SearchOperation.LIKE);
//        var issue2 = new SearchIssueDto("subject", "Issue1", SearchOperation.LIKE);
//        var issue3 = new SearchIssueDto("id","1",SearchOperation.EQUAL);
//        list.add(issue1);
//        list.add(issue2);
//        list.add(issue3);
//        mockMvc.perform(post("/issues/filters")
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(list)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalElements", is(1)))
//                .andExpect(jsonPath("$.content.[0].subject", is("Issue1")))
//                .andExpect(jsonPath("$.content.[0].description", is("Issue with bug")))
//                .andExpect(jsonPath("$.content.[0].id", is(1)));
//    }
//
//    @Test
//    void shouldReturnFourIssuesWithFilterSubject() throws Exception {
//        list = new ArrayList<>();
//        var issue = new SearchIssueDto();
//        issue.setKey("subject");
//        issue.setValue("Issue1");
//        issue.setOperation(SearchOperation.LIKE);
//        list.add(issue);
//        mockMvc.perform(post("/issues/filters")
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(list)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalElements", is(3)))
//                .andExpect(jsonPath("$.content.[0].subject", is("Issue1")))
//                .andExpect(jsonPath("$.content.[1].subject", is("Issue1")))
//                .andExpect(jsonPath("$.content.[2].subject", is("Issue1")));
//    }
}
