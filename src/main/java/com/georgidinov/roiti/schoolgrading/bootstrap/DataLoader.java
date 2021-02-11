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
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private Set<Mark> markSet = new HashSet<>();
    private Set<Student> studentSet = new HashSet<>();
    private Set<Course> courseSet = new HashSet<>();

    //== constructors ==
    @Autowired
    public DataLoader(MarkRepository markRepository,
                      StudentRepository studentRepository,
                      CourseRepository courseRepository) {
        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }


    //== public methods ==
    @Override
    public void run(String... args) throws Exception {
        List<MyBeanContainer> beanContainers = this.fetchData();
        this.processData(beanContainers);
        log.info("Processed {} Marks", this.markSet.size());
        log.info("Processed {} Courses", this.courseSet.size());
        log.info("Processed {} Students", this.studentSet.size());
        log.info("After relations...");
        MyBeanContainer container = beanContainers.get(0);
        log.info("Student = {}", container.getStudent());
        log.info("Course = {}", container.getCourse());
        log.info("Mark = {}", container.getMark());
    }

    private List<MyBeanContainer> fetchData() {
        Path path = FileSystems.getDefault().getPath("DomainData/marks.csv");
        List<MyBeanContainer> beanContainers = new ArrayList<>();
        try {
            beanContainers = new CsvToBeanBuilder<MyBeanContainer>(new CSVReader(Files.newBufferedReader(path)))
                    .withType(MyBeanContainer.class).build().parse();
            log.info("CSV file loaded successfully!");
            MyBeanContainer container = beanContainers.get(0);
            log.info("Student = {}", container.getStudent());
            log.info("Course = {}", container.getCourse());
            log.info("Mark = {}", container.getMark());
        } catch (IOException e) {
            System.out.println("Error reading csv file " + e.getMessage());
        }
        return beanContainers;
    }


    private void processData(List<MyBeanContainer> beanContainers) {
        log.info("processData() called...");
        beanContainers.forEach(this::createRelations);
        log.info("createRelations complete...");
    }

    private void createRelations(MyBeanContainer beanContainer) {
        Mark mark = beanContainer.getMark();
        Student student = beanContainer.getStudent();
        Course course = beanContainer.getCourse();
        //todo perform validations
        student.addCourse(course);
        student.addMark(mark);
        course.addMark(mark);

        this.markSet.add(mark);
        this.studentSet.add(student);
        this.courseSet.add(course);
    }

}