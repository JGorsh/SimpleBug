package com.example.service;


import com.example.model.domain.Issue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FilterSpecification{

   public Specification<Issue> getSearchSpecification(Map<String, String> params) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : params.entrySet()){
                String key = entry.getKey();
                Predicate equal = builder.equal(root.get(key), params.get(key));
                predicates.add(equal);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
