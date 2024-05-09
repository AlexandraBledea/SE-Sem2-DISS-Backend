package com.project.diss.converters;

import com.project.diss.dto.CommentDto;
import com.project.diss.dto.CommentSaveDto;
import com.project.diss.dto.CommentUpdateDto;
import com.project.diss.persistance.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentConverter {
    UserConverter userConverter;

    @Autowired
    public CommentConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public CommentEntity convertCommentDtoToCommentEntity(CommentDto dto, UserEntity userEntity, DocumentEntity documentEntity) {
        if (dto == null) {
            return null;
        }

        CommentEntity comment = new CommentEntity();

        comment.setDocument(documentEntity);
        comment.setUser(userEntity);
        comment.setText(dto.getText());
        comment.setId(dto.getId());
        comment.setCreated(dto.getCreated());

        return comment;
    }

    public CommentEntity convertSaveCommentDtoToCommentEntity(CommentSaveDto dto, UserEntity user, DocumentEntity document) {
        if (dto == null) {
            return null;
        }

        CommentEntity comment = new CommentEntity();

        comment.setText(dto.getText());
        comment.setDocument(document);
        comment.setUser(user);
        return comment;
    }

    public CommentEntity convertUpdateCommentDtoToCommentEntity(CommentUpdateDto dto, UserEntity user, DocumentEntity document) {
        if (dto == null) {
            return null;
        }

        CommentEntity comment = new CommentEntity();
        comment.setId(dto.getId());
        comment.setText(dto.getText());
        comment.setDocument(document);
        comment.setUser(user);
        return comment;
    }

    public CommentDto convertCommentEntityToCommentDto(CommentEntity entity) {
        if (entity == null) {
            return null;
        }

        CommentDto comment = new CommentDto();

        comment.setId(entity.getId());
        comment.setText(entity.getText());
        comment.setCreated(entity.getCreated());
        comment.setDocumentId(entity.getDocument().getId());
        comment.setUser(this.userConverter.convertUserEntityToUserDocumentDto(entity.getUser()));
        return comment;
    }

    public List<CommentDto> convertCommentEntitiesToCommentDtos(List<CommentEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<CommentDto> list = new ArrayList<>(entities.size());
        for (CommentEntity comment : entities) {
            list.add(convertCommentEntityToCommentDto(comment));
        }

        return list;
    }
}