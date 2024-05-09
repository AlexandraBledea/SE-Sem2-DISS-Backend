package com.project.diss.service;

import com.project.diss.converters.CommentConverter;
import com.project.diss.dto.CommentDto;
import com.project.diss.dto.CommentSaveDto;
import com.project.diss.dto.CommentUpdateDto;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.persistance.*;
import com.project.diss.persistance.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CommentService {

    DocumentRepository documentRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;
    CommentConverter commentConverter;

    @Autowired
    public CommentService(UserRepository userRepository, DocumentRepository documentRepository,
                          CommentRepository commentRepository, CommentConverter commentConverter) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    public CommentDto createComment(CommentSaveDto comment) throws EntityNotFoundException, RequestNotValidException {
        log.info("Creating comment: {}", comment);
        Optional<UserEntity> user = userRepository.findById(comment.getUserId());
        Optional<DocumentEntity> document = documentRepository.findById(comment.getDocumentId());

        if (user.isPresent() && document.isPresent()) {
            if (document.get() instanceof EmployeeDocumentEntity && !((EmployeeDocumentEntity) document.get()).getVisibility()) {
                log.error("Could not save database entry because document with id {} has visibility false", comment.getDocumentId());
                throw new RequestNotValidException();
            }
            CommentEntity commentEntity = commentConverter.convertSaveCommentDtoToCommentEntity(comment, user.get(), document.get());
            commentEntity = commentRepository.save(commentEntity);
            return commentConverter.convertCommentEntityToCommentDto(commentEntity);
        }
        log.error("Could not save database entry because user with id {} or document with id {} doesn't exist", comment.getUserId(), comment.getDocumentId());
        throw new EntityNotFoundException();
    }

    public List<CommentDto> getDocumentComments(Long id) throws EntityNotFoundException, RequestNotValidException {

        Optional<DocumentEntity> documentEntity = documentRepository.findById(id);
        if (documentEntity.isPresent()) {
            if (documentEntity.get() instanceof EmployeeDocumentEntity && !((EmployeeDocumentEntity) documentEntity.get()).getVisibility()) {
                log.error("Could not save database entry because document with id {} has visibility false", id);
                throw new RequestNotValidException();
            }

            List<CommentEntity> comments = commentRepository.findByDocumentId(id);
            if (comments.isEmpty()) {
                return new ArrayList<>();
            }
            return commentConverter.convertCommentEntitiesToCommentDtos(comments);
        }
        log.error("Could not retrieve comments for employee document with id {}", id);
        throw new EntityNotFoundException();
    }

    public void deleteComment(Long id) throws EntityNotFoundException, RequestNotValidException {
        Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        if (commentEntity.isEmpty()) {
            log.info("Could not find comment with id {}", id);
            throw new EntityNotFoundException();
        }

        if (commentEntity.get().getDocument() instanceof EmployeeDocumentEntity && !((EmployeeDocumentEntity) commentEntity.get().getDocument()).getVisibility()) {
            log.error("Could not save database entry because document with id {} has visibility false", id);
            throw new RequestNotValidException();
        }
        commentRepository.deleteById(id);
    }

    public CommentDto updateComment(CommentUpdateDto comment) throws EntityNotFoundException, RequestNotValidException {
        Optional<CommentEntity> commentEntity = commentRepository.findById(comment.getId());

        if (commentEntity.isEmpty()) {
            log.info("Could not find comment with id {}", comment.getId());
            throw new EntityNotFoundException();
        }

        if (commentEntity.get().getDocument() instanceof EmployeeDocumentEntity && !((EmployeeDocumentEntity) commentEntity.get().getDocument()).getVisibility()) {
            log.error("Could not save database entry because document with id {} has visibility false", commentEntity.get().getDocument().getId());
            throw new RequestNotValidException();
        }

        CommentEntity updatedCommentEntity = commentConverter.convertUpdateCommentDtoToCommentEntity(comment, commentEntity.get().getUser(), commentEntity.get().getDocument());
        return commentConverter.convertCommentEntityToCommentDto(commentRepository.save(updatedCommentEntity));
    }
}