package com.georgidinov.roiti.schoolgrading.api.v1.mapper;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.COURSE_BASE_URL;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    default CourseDTO courseToCourseDTO(Course course) {
        return CourseDTO.builder()
                .name(course.getName())
                .courseUrl(COURSE_BASE_URL + "/" + course.getId())
                .build();
    }

    @Mapping(source = "name", target = "name")
    Course courseDTOToCourse(CourseDTO courseDTO);
}
