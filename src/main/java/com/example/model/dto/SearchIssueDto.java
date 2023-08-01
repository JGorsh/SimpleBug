package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchIssueDto {
    private String key;
    private Object value;
    private SearchOperation operation;
}
