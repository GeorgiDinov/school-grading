package com.georgidinov.roiti.schoolgrading.api.v1.mapper;

import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_MARK_DATE_TIME_FORMAT;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.MARK_BASE_URL;

@Mapper
public interface MarkMapper {

    MarkMapper INSTANCE = Mappers.getMapper(MarkMapper.class);


    @Mapping(source = "mark.mark", target = "mark")
    @Mapping(expression = "java(markDateToString(mark.getMarkDate()))", target = "date")
    @Mapping(source = "mark.course.name", target = "courseName")
    @Mapping(source = "mark.student.name", target = "studentName")
    @Mapping(expression = "java(mapMarkUrl(mark.getId()))", target = "markUrl")
    MarkDTO markToMarkDTO(Mark mark);


    @Mapping(source = "markDTO.mark", target = "mark")
    Mark markDTOToMark(MarkDTO markDTO);

    default String markDateToString(LocalDateTime localDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ENTITY_MARK_DATE_TIME_FORMAT);
        return dtf.format(localDateTime);
    }

    default String mapMarkUrl(Long id) {
        return MARK_BASE_URL + "/" + id;
    }

}
