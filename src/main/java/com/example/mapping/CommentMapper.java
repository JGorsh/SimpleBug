package com.example.mapping;

import com.example.model.domain.Comment;
import com.example.model.dto.CommentDto;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {

    CommentDto fromEntity(Comment comment);

}
