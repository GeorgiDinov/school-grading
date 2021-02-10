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

    private Set<Mark> marks = new HashSet<>();
    private Set<Student> students = new HashSet<>();
    private Set<Course> courses = new HashSet<>();


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
        this.marks.addAll(this.fetchData());
        this.processData(this.marks);
        log.info("Courses = {}", this.courses.size());
        //this.courses.forEach(System.out::println);
        log.info("Students = {}", this.students.size());
        log.info("Marks = {}", this.marks.size());
        //this.saveDataIntoDBMS();
    }

    private List<Mark> fetchData() {
        Path path = FileSystems.getDefault().getPath("DomainData/marks.csv");
        List<Mark> marks = new ArrayList<>();
        try {
            marks = new CsvToBeanBuilder<Mark>(new CSVReader(Files.newBufferedReader(path)))
                    .withType(Mark.class).build().parse();
            log.info("CSV file loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading csv file " + e.getMessage());
        }
        return marks;
    }


    private void processData(Set<Mark> marks) {
        marks.forEach(this::createRelations);
        log.info("Relations build successfully!");
    }

    private void createRelations(Mark mark) {
        //todo validations for mark
        Student student = mark.getStudent();
        Course course = mark.getCourse();
        //todo validations for student and course
        student.addMark(mark);
        course.addMark(mark);
        student.addCourse(course);
        course.addStudent(student);

        this.students.add(student);
        this.courses.add(course);
    }


    private void saveDataIntoDBMS() {
        log.info("saveDataIntoDBMS called...");

        this.courseRepository.saveAll(this.courses);
        log.info("Courses successfully added to the database...");

        this.studentRepository.saveAll(this.students);
        log.info("Students successfully added to the database...");

        this.markRepository.saveAll(this.marks);
        log.info("Marks successfully added to the database...");
    }

}