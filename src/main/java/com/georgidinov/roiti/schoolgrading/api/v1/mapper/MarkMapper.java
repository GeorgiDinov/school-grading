package com.georgidinov.roiti.schoolgrading.api.v1.mapper;

import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MarkMapper {

    MarkMapper INSTANCE = Mappers.getMapper(MarkMapper.class);


    @Mapping(source = "mark.mark", target = "mark")
    @Mapping(source = "mark.markDate", target = "date")
    @Mapping(source = "mark.course.name", target = "courseName")
    @Mapping(source = "mark.student.name", target = "studentName")
    MarkDTO markToMarkDTO(Mark mark);

}
