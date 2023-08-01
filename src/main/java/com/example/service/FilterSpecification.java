package com.example.service;


import com.example.model.domain.Issue;
import com.example.model.dto.SearchIssueDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FilterSpecification{

   public Specification<Issue> getSearchSpecification(List<SearchIssueDto> searchIssueDtos) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            for(SearchIssueDto searchIssueDto : searchIssueDtos){
                Predicate equal = builder.equal(root.get(searchIssueDto.getKey()), searchIssueDto.getValue());
                predicates.add(equal);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
