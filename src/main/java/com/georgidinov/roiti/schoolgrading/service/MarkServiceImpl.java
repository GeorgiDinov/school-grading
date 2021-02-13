package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.MarkMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_MARK_NOT_FOUND;

@Slf4j
@Service
public class MarkServiceImpl implements MarkService {

    //== fields ==
    private final MarkRepository markRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final BaseNamedEntityValidator baseNamedEntityValidator;
    private final MarkMapper markMapper;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository,
                           CourseRepository courseRepository,
                           StudentRepository studentRepository,
                           BaseNamedEntityValidator baseNamedEntityValidator, MarkMapper markMapper) {
        this.markRepository = markRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.baseNamedEntityValidator = baseNamedEntityValidator;
        this.markMapper = markMapper;
    }


    @Override
    public MarkListDTO findAllMarks() {
        log.info("MarkServiceImp::findAllMarks");
        return new MarkListDTO(
                this.markRepository.findAll()
                        .stream()
                        .map(markMapper::markToMarkDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public MarkDTO findMarkById(Long id) {
        log.info("MarkServiceImpl::findMarkById -> id passed = {}", id);
        return this.markRepository.findById(id)
                .map(markMapper::markToMarkDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_MARK_NOT_FOUND, id))
                );
    }

    @Override
    public MarkDTO saveMark(MarkDTO markDTO) throws EntityValidationException {
        log.info("MarkServiceImpl::saveMark -> markDTO passed = {}", markDTO);
        this.verifyMarkDTO(markDTO);
        Mark mark = this.relateMark(markDTO);
        return saveMarkToDatabase(mark);
    }

    @Override
    public MarkDTO updateMark(Long id, MarkDTO markDTO) throws EntityValidationException {
        log.info("MarkServiceImpl::saveMark -> id passed = {} markDTO passed = {}", id, markDTO);
        this.verifyMarkDTO(markDTO);
        Mark mark = this.markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_MARK_NOT_FOUND, id)));
        mark.setMark(markDTO.getMark());
        mark.setMarkDate(LocalDateTime.now());
        return this.saveMarkToDatabase(mark);
    }

    @Override
    public void deleteMarkById(Long id) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.info("Thread was interrupted {}", e.getMessage());
        }
        log.info("MarkServiceImpl::deleteMarkById -> id passed = {}", id);
        this.markRepository.deleteById(id);
    }

    //== private methods ==
    private MarkDTO saveMarkToDatabase(Mark mark) {
        Mark savedMark = this.markRepository.save(mark);
        return this.markMapper.markToMarkDTO(savedMark);
    }

    private Mark relateMark(MarkDTO markDTO) {
        Course markCourse = getCourse(markDTO);
        Student markStudent = getStudent(markDTO);
        Mark mark = markMapper.markDTOToMark(markDTO);
        mark.setMarkDate(LocalDateTime.now());
        mark.setCourse(markCourse);
        mark.setStudent(markStudent);
        return mark;
    }


    private Student getStudent(MarkDTO markDTO) {
        return this.studentRepository.findStudentByName(markDTO.getStudentName())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Student " + markDTO.getStudentName() + " not found"));
    }

    private Course getCourse(MarkDTO markDTO) {
        return courseRepository.findCourseByName(markDTO.getCourseName())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Course " + markDTO.getCourseName() + " not found"));
    }

    private void verifyMarkDTO(MarkDTO markDTO) throws EntityValidationException {
        validateMarkValue(markDTO);
        validateCourseNames(markDTO);
    }

    private void validateMarkValue(MarkDTO markDTO) throws EntityValidationException {
        if (markDTO.getMark() < 2.00d) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS);
        }
        if (markDTO.getMark() > 6.00d) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE);
        }
    }

    private void validateCourseNames(MarkDTO markDTO) throws EntityValidationException {
        StudentDTO studentDTO = StudentDTO.builder().name(markDTO.getStudentName()).build();
        CourseDTO courseDTO = CourseDTO.builder().name(markDTO.getCourseName()).build();
        this.baseNamedEntityValidator.validate(studentDTO);
        this.baseNamedEntityValidator.validate(courseDTO);
    }

}