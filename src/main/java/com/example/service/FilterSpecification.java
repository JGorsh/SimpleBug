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
                switch (searchIssueDto.getOperation()){
                    case LIKE:
                        Predicate like = builder.like(builder.lower(root.get(searchIssueDto.getKey())), "%" + searchIssueDto.getValue().toString().toLowerCase() + "%");
                        predicates.add(like);
                        break;
                    case EQUAL:
                        Predicate equal = builder.equal(root.get(searchIssueDto.getKey()), searchIssueDto.getValue());
                        predicates.add(equal);
                        break;
                    case IN:
                        Predicate in = builder.in(root.get(searchIssueDto.getKey())).value(searchIssueDto.getValue());
                        predicates.add(in);
                        break;
                }

                Predicate equal = builder.equal(root.get(searchIssueDto.getKey()), searchIssueDto.getValue());
                predicates.add(equal);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
