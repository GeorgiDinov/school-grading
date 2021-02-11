package com.georgidinov.roiti.schoolgrading.bootstrap;

import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
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
    private final Set<Mark> markSet = new HashSet<>();

    //== constructors ==
    @Autowired
    public DataLoader(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }


    //== public methods ==
    @Override
    public void run(String... args) throws Exception {
        List<MyBeanContainer> beanContainers = this.fetchData();
        this.processData(beanContainers);
        log.info("Saving into database...");
        this.markRepository.saveAll(markSet);
        log.info("Saved...");
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
    }

}