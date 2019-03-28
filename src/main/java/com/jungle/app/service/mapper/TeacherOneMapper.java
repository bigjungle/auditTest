package com.jungle.app.service.mapper;

import com.jungle.app.domain.*;
import com.jungle.app.service.dto.TeacherOneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TeacherOne and its DTO TeacherOneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeacherOneMapper extends EntityMapper<TeacherOneDTO, TeacherOne> {



    default TeacherOne fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeacherOne teacherOne = new TeacherOne();
        teacherOne.setId(id);
        return teacherOne;
    }
}
