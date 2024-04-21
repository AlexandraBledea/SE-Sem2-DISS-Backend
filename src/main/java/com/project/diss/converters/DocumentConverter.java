package com.project.diss.converters;

import com.project.diss.controller.dto.EmployeeDocumentDto;
import com.project.diss.controller.dto.UserDto;
import com.project.diss.persistance.entity.DocumentEntity;
import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentConverter {

    public EmployeeDocumentDto convertEmployeeDocumentEntityToEmployeeDocumentDto(EmployeeDocumentEntity entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDocumentDto employeeDocument = new EmployeeDocumentDto();

        employeeDocument.setId(entity.getId());
        employeeDocument.setTitle(entity.getDocument().getTitle());
        employeeDocument.setText(entity.getDocument().getText());
        employeeDocument.setDocument(entity.getDocument().getDocument());
        employeeDocument.setKeywords(entity.getDocument().getKeywords());
        employeeDocument.setCreated(entity.getDocument().getCreated());
        employeeDocument.setLastModified(entity.getDocument().getLastModified());
        employeeDocument.setVisibility(entity.getVisibility());
        employeeDocument.setUserId(entity.getDocument().getUser().getId());
        return employeeDocument;
    }

    public DocumentEntity convertEmployeeDocumentDtoToDocumentEntity(EmployeeDocumentDto dto, UserEntity entity) {
        if (dto == null) {
            return null;
        }

        DocumentEntity document = new DocumentEntity();

        document.setTitle(dto.getTitle());
        document.setText(dto.getText());
        document.setDocument(dto.getDocument());
        document.setKeywords(dto.getKeywords());
        document.setCreated(dto.getCreated());
        document.setLastModified(dto.getLastModified());
        document.setUser(entity);

        return document;
    }
    public EmployeeDocumentEntity convertEmployeeDocumentDtoToEmployeeDocumentEntity(EmployeeDocumentDto dto, UserEntity entity) {
        if (dto == null) {
            return null;
        }

        EmployeeDocumentEntity employeeDocument = new EmployeeDocumentEntity();
        DocumentEntity document = convertEmployeeDocumentDtoToDocumentEntity(dto, entity);

        employeeDocument.setId(dto.getId());
        employeeDocument.setDocument(document);
        employeeDocument.setVisibility(dto.isVisibility());
        return employeeDocument;
    }

    public List<EmployeeDocumentDto> convertEmployeeDocumentEntitiesToEmployeeDocumentDtos(List<EmployeeDocumentEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<EmployeeDocumentDto> list = new ArrayList<>(entities.size());
        for (EmployeeDocumentEntity employeeDocument : entities) {
            list.add(convertEmployeeDocumentEntityToEmployeeDocumentDto(employeeDocument));
        }

        return list;
    }

}
