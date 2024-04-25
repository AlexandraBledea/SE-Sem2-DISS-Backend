package com.project.diss.converters;

import com.project.diss.dto.SaveUserDto;
import com.project.diss.dto.UserDto;
import com.project.diss.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserConverter(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public UserEntity convertSaveUserDtoToUserEntity(SaveUserDto saveUserDto) {
        UserEntity user = new UserEntity();
        user.setEmail(saveUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(saveUserDto.getPassword()));
        user.setFirstname(saveUserDto.getFirstname());
        user.setLastname(saveUserDto.getLastname());
        user.setPhoneNumber(saveUserDto.getPhoneNumber());
        user.setRole(saveUserDto.getRole());
        user.setDepartment(saveUserDto.getDepartment());
        user.setLocation(saveUserDto.getLocation());
        user.setLevel(saveUserDto.getLevel());
        user.setPoints(saveUserDto.getPoints());
        user.setType(saveUserDto.getType());
        return user;
    }

    public UserDto convertUserEntityToUserDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserDto user = new UserDto();

        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setFirstname(entity.getFirstname());
        user.setLastname(entity.getLastname());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setRole(entity.getRole());
        user.setDepartment(entity.getDepartment());
        user.setLocation(entity.getLocation());
        user.setLevel(entity.getLevel());
        user.setPoints(entity.getPoints());
        user.setType(entity.getType());
        return user;
    }

    public UserEntity convertUserDtoToUserEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        UserEntity user = new UserEntity();

        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());
        user.setDepartment(dto.getDepartment());
        user.setLocation(dto.getLocation());
        user.setLevel(dto.getLevel());
        user.setPoints(dto.getPoints());
        user.setType(dto.getType());
        return user;
    }

    public List<UserDto> convertUserEntitiesToUserDtos(List<UserEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<UserDto> list = new ArrayList<>(entities.size());
        for (UserEntity user : entities) {
            list.add(convertUserEntityToUserDto(user));
        }

        return list;
    }

    public List<UserEntity> convertUserDtosToUserEntities(List<UserDto> dtos) {
        if (dtos == null) {
            return null;
        }

        List<UserEntity> list = new ArrayList<>(dtos.size());
        for (UserDto userDto : dtos) {
            list.add(convertUserDtoToUserEntity(userDto));
        }

        return list;
    }
}
