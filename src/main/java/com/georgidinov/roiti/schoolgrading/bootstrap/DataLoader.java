package com.georgidinov.roiti.schoolgrading.bootstrap;

import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
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

    private final Set<Mark> markSet = new HashSet<>();
    private final Set<Student> studentSet = new HashSet<>();
    private final Set<Course> courseSet = new HashSet<>();

    //== constructors ==
    @Autowired
    public DataLoader(MarkRepository markRepository,
                      CourseRepository courseRepository,
                      StudentRepository studentRepository) {
        this.markRepository = markRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }


    //== public methods ==
    @Override
    public void run(String... args) throws Exception {
        List<MyBeanContainer> beanContainers = this.fetchData();
        this.processData(beanContainers);
        log.info("Students = {}", studentSet.size());
        log.info("Courses = {}", courseSet.size());
        log.info("Marks = {}", markSet.size());
        log.info("Saving into database...");
        this.markRepository.saveAll(markSet);
        this.courseRepository.saveAll(courseSet);
        this.studentRepository.saveAll(studentSet);
        log.info("Saved...");

        log.info("Attempting delete operation on student");
        this.studentRepository.deleteById(1L);

        log.info("Attempting delete operation on course");
        this.courseRepository.deleteById(1L);

        log.info("Attempting delete operation on mark");
        this.markRepository.deleteById(9L);

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


    private void processData(List<MyBeanContainer> beanContainers) {
        log.info("processing data relations...");
        beanContainers.forEach(this::createRelations);
        log.info("processing data relations complete...");
    }

    private void createRelations(MyBeanContainer beanContainer) {
        Mark mark = beanContainer.getMark();
        Student student = beanContainer.getStudent();
        Course course = beanContainer.getCourse();
        //todo perform validations
        student.addMark(mark);
        course.addMark(mark);

        this.markSet.add(mark);
        this.studentSet.add(student);
        this.courseSet.add(course);
    }

}