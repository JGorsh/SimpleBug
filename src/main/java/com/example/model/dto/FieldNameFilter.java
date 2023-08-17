package com.example.model.dto;


public enum FieldNameFilter {
    ID("id"),
    SUBJECT("subject"),
    DESCRIPTION("description");

    private String name;

    FieldNameFilter(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
