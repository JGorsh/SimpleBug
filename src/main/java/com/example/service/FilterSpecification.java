package com.example.service;


import com.example.model.domain.Issue;
import com.example.model.dto.SearchIssueDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilterSpecification{

   public Specification<Issue> getSearchSpecification(List<SearchIssueDto> searchIssueDtos) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            for(SearchIssueDto searchIssueDto : searchIssueDtos){
                switch (searchIssueDto.getKey()) {
                    case SUBJECT:
                        Predicate likeSubject = builder.like(builder.lower(root.get(searchIssueDto.getKey().getName())),
                                "%" + searchIssueDto.getValue().toString().toLowerCase() + "%");
                        predicates.add(likeSubject);
                        break;
                    case DESCRIPTION:
                        Predicate likeDesc = builder.like(builder.lower(root.get(searchIssueDto.getKey().getName())),
                                "%" + searchIssueDto.getValue().toString().toLowerCase() + "%");
                        predicates.add(likeDesc);
                        break;
                    case ID:
                        Predicate equal = builder.equal(root.get(searchIssueDto.getKey().getName()), searchIssueDto.getValue());
                        predicates.add(equal);
                        break;
                }
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
