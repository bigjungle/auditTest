package com.jungle.app.service.mapper;

import com.jungle.app.domain.*;
import com.jungle.app.service.dto.StudentOneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentOne and its DTO StudentOneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentOneMapper extends EntityMapper<StudentOneDTO, StudentOne> {



    default StudentOne fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentOne studentOne = new StudentOne();
        studentOne.setId(id);
        return studentOne;
    }
}
