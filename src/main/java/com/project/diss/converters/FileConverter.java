package com.project.diss.converters;

import com.project.diss.dto.FileDto;
import com.project.diss.persistance.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileConverter {

    public FileDto convertFileEntityToFileDto(FileEntity entity) {
        if (entity == null) {
            return null;
        }

        FileDto file = new FileDto();

        file.setId(entity.getId());
        file.setName(entity.getName());
        file.setType(entity.getType());
        file.setBuffer(entity.getBuffer());
        return file;
    }

    public FileEntity convertFileDtoToFileEntity(FileDto dto) {
        if (dto == null) {
            return null;
        }

        FileEntity file = new FileEntity();

        file.setId(dto.getId());
        file.setName(dto.getName());
        file.setType(dto.getType());
        file.setBuffer(dto.getBuffer());
        return file;
    }
}
