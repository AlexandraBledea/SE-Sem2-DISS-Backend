package com.project.diss.converters;

import com.project.diss.dto.EmployeeDocumentDto;
import com.project.diss.dto.SaveEmployeeDocumentDto;
import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import com.project.diss.persistance.entity.FileEntity;
import com.project.diss.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDocumentConverter {

    UserConverter userConverter;
    FileConverter fileConverter;

    @Autowired
    public EmployeeDocumentConverter(UserConverter userConverter, FileConverter fileConverter) {
        this.userConverter = userConverter;
        this.fileConverter = fileConverter;
    }

    public EmployeeDocumentEntity convertSaveEmployeeDocumentDtoToEmployeeDocumentEntity(SaveEmployeeDocumentDto dto, UserEntity user, FileEntity file) {
        if (dto == null) {
            return null;
        }

        EmployeeDocumentEntity employeeDocument = new EmployeeDocumentEntity();

        employeeDocument.setId(dto.getId());
        employeeDocument.setTitle(dto.getTitle());
        employeeDocument.setText(dto.getText());
        employeeDocument.setKeywords(dto.getKeywords());
        employeeDocument.setVisibility(dto.isVisibility());
        employeeDocument.setUser(user);
        employeeDocument.setFile(file);
        return employeeDocument;
    }

    public EmployeeDocumentDto convertEmployeeDocumentEntityToEmployeeDocumentDto(EmployeeDocumentEntity entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDocumentDto employeeDocument = new EmployeeDocumentDto();

        employeeDocument.setId(entity.getId());
        employeeDocument.setTitle(entity.getTitle());
        employeeDocument.setText(entity.getText());
        employeeDocument.setKeywords(entity.getKeywords());
        employeeDocument.setLastModified(entity.getLastModified());
        employeeDocument.setVisibility(entity.getVisibility());
        employeeDocument.setUser(this.userConverter.convertUserEntityToUserDto(entity.getUser()));
        employeeDocument.setFile(this.fileConverter.convertFileEntityToFileDto(entity.getFile()));
        return employeeDocument;
    }

    public EmployeeDocumentEntity convertEmployeeDocumentDtoToEmployeeDocumentEntity(EmployeeDocumentDto dto) {
        if (dto == null) {
            return null;
        }

        EmployeeDocumentEntity employeeDocument = new EmployeeDocumentEntity();

        employeeDocument.setId(dto.getId());
        employeeDocument.setTitle(dto.getTitle());
        employeeDocument.setText(dto.getText());
        employeeDocument.setKeywords(dto.getKeywords());
        employeeDocument.setLastModified(dto.getLastModified());
        employeeDocument.setVisibility(dto.isVisibility());
        employeeDocument.setUser(this.userConverter.convertUserDtoToUserEntity(dto.getUser()));
        employeeDocument.setFile(this.fileConverter.convertFileDtoToFileEntity(dto.getFile()));
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
