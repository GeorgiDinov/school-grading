package com.georgidinov.roiti.schoolgrading.api.v1.mapper;

import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.STUDENT_BASE_URL;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    default StudentDTO studentToStudentDTO(Student student) {
        return StudentDTO.builder()
                .name(student.getName())
                .studentUrl(STUDENT_BASE_URL + "/" + student.getId())
                .build();
    }

    @Mapping(source = "name", target = "name")
    Student studentDTOToStudent(StudentDTO studentDTO);

}