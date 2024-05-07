package com.project.diss.converters;

import com.project.diss.dto.CompanyDocumentDto;
import com.project.diss.dto.CompanyDocumentGetDto;
import com.project.diss.dto.CompanyDocumentSaveDto;
import com.project.diss.persistance.entity.CompanyDocumentEntity;
import com.project.diss.persistance.entity.FileEntity;
import com.project.diss.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDocumentConverter {
    UserConverter userConverter;
    FileConverter fileConverter;

    @Autowired
    public CompanyDocumentConverter(UserConverter userConverter, FileConverter fileConverter) {
        this.userConverter = userConverter;
        this.fileConverter = fileConverter;
    }

    public CompanyDocumentEntity convertSaveCompanyDocumentDtoToCompanyDocumentEntity(CompanyDocumentSaveDto dto, UserEntity user, FileEntity file) {
        if (dto == null) {
            return null;
        }
        CompanyDocumentEntity companyDocument = new CompanyDocumentEntity();
        companyDocument.setId(dto.getId());
        companyDocument.setTitle(dto.getTitle());
        companyDocument.setText(dto.getText());
        companyDocument.setKeywords(dto.getKeywords());
        companyDocument.setUser(user);
        companyDocument.setFile(file);
        return companyDocument;
    }

    public CompanyDocumentDto convertCompanyDocumentEntityToCompanyDocumentDto(CompanyDocumentEntity entity) {
        if (entity == null) {
            return null;
        }
        CompanyDocumentDto companyDocument = new CompanyDocumentDto();
        companyDocument.setId(entity.getId());
        companyDocument.setTitle(entity.getTitle());
        companyDocument.setText(entity.getText());
        companyDocument.setKeywords(entity.getKeywords());
        companyDocument.setLastModified(entity.getLastModified());
        companyDocument.setUser(this.userConverter.convertUserEntityToUserDocumentDto(entity.getUser()));
        companyDocument.setFile(this.fileConverter.convertFileEntityToFileDto(entity.getFile()));
        return companyDocument;
    }

    public CompanyDocumentGetDto convertCompanyDocumentEntityToCompanyDocumentGetDto(CompanyDocumentEntity entity) {
        if (entity == null) {
            return null;
        }
        CompanyDocumentGetDto companyDocument = new CompanyDocumentGetDto();
        companyDocument.setId(entity.getId());
        companyDocument.setTitle(entity.getTitle());
        companyDocument.setLastModified(entity.getLastModified());
        companyDocument.setUser(this.userConverter.convertUserEntityToUserDocumentDto(entity.getUser()));
        return companyDocument;
    }

    public List<CompanyDocumentGetDto> convertCompanyDocumentEntitiesToCompanyDocumentGetDtos(List<CompanyDocumentEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<CompanyDocumentGetDto> companyDocuments = new ArrayList<>(entities.size());
        for (CompanyDocumentEntity entity : entities) {
            companyDocuments.add(this.convertCompanyDocumentEntityToCompanyDocumentGetDto(entity));
        }
        return companyDocuments;
    }
}
