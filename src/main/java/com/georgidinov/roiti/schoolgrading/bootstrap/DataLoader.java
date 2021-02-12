package com.georgidinov.roiti.schoolgrading.bootstrap;

import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseEntityValidator;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import com.georgidinov.roiti.schoolgrading.validation.MarkValidator;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    //== fields ==
    private final MarkRepository markRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    private final MarkValidator markValidator;
    private final BaseEntityValidator baseEntityValidator;
    private final BaseNamedEntityValidator baseNamedEntityValidator;

    private final Set<Mark> markSet = new HashSet<>();
    private final Set<Student> studentSet = new HashSet<>();
    private final Set<Course> courseSet = new HashSet<>();

    //== constructors ==
    @Autowired
    public DataLoader(MarkRepository markRepository,
                      CourseRepository courseRepository,
                      StudentRepository studentRepository,
                      MarkValidator markValidator,
                      BaseEntityValidator baseEntityValidator,
                      BaseNamedEntityValidator baseNamedEntityValidator) {
        this.markRepository = markRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.markValidator = markValidator;
        this.baseEntityValidator = baseEntityValidator;
        this.baseNamedEntityValidator = baseNamedEntityValidator;
    }


    //== public methods ==
    @Override
    public void run(String... args) throws Exception {
        List<MyBeanContainer> beanContainers = this.fetchData();
        this.processData(beanContainers);
        log.info("Students = {}", studentSet.size());
        log.info("Courses = {}", courseSet.size());
        log.info("Marks = {}", markSet.size());
        log.info("Saving data into database...");
        this.markRepository.saveAll(markSet);
        this.courseRepository.saveAll(courseSet);
        this.studentRepository.saveAll(studentSet);
        log.info("Data saved successfully...");
    }


    //== private methods ==
    private List<MyBeanContainer> fetchData() {
        Path path = FileSystems.getDefault().getPath("DomainData/marks.csv");
        List<MyBeanContainer> beanContainers = new ArrayList<>();
        try {
            beanContainers = new CsvToBeanBuilder<MyBeanContainer>(new CSVReader(Files.newBufferedReader(path)))
                    .withType(MyBeanContainer.class).build().parse();
            log.info("CSV file loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading csv file " + e.getMessage());
        }
        return beanContainers;
    }


    private void processData(List<MyBeanContainer> beanContainers) throws EntityValidationException {
        log.info("validating and processing data relations...");
        for (MyBeanContainer beanContainer : beanContainers) {
            createRelations(beanContainer);
        }
        log.info("processing data relations and validation complete...");
    }

    private void createRelations(MyBeanContainer beanContainer) throws EntityValidationException {
        this.validateContainer(beanContainer);

        Mark mark = beanContainer.getMark();
        Student student = beanContainer.getStudent();
        Course course = beanContainer.getCourse();

        mark.setCourse(course);
        mark.setStudent(student);

        this.markSet.add(mark);
        this.studentSet.add(student);
        this.courseSet.add(course);
    }

    private void validateContainer(MyBeanContainer container) throws EntityValidationException {
        Mark mark = container.getMark();
        this.markValidator.validate(mark);

        Student student = container.getStudent();
        this.baseEntityValidator.validate(student);
        this.baseNamedEntityValidator.validate(student);

        Course course = container.getCourse();
        this.baseEntityValidator.validate(course);
        this.baseNamedEntityValidator.validate(course);
    }

}