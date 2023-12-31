package com.example;


import com.example.mapping.IssueMapperImpl;
import com.example.model.domain.Issue;
import com.example.model.dto.IssueDto;
import com.example.repository.IssueRepository;
import com.example.repository.ProjectRepository;
import com.example.repository.PersonRepository;
import com.example.service.IssueServiceRdb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

    @InjectMocks
    IssueServiceRdb issueServiceRdb;

    @Mock
    IssueRepository issueRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    IssueMapperImpl mapper;

    @Test
    void shouldReturnOneEntity(){
        //given
        Issue issue = new Issue();
        issue.setDescription("Test description");
        issue.setId(1l);
        issue.setSubject("Test");
        IssueDto issueDto = new IssueDto();
        issueDto.setDescription("Test description");
        issueDto.setId(1L);
        issueDto.setSubject("Test");

        Mockito.when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        Mockito.when(mapper.fromEntity(issue)).thenReturn(issueDto);
        var result = issueServiceRdb.getOne(1L);
        System.out.println(result);
        assertEquals( result.getSubject(), issue.getSubject() );

    }

}
