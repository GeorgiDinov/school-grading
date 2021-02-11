package com.georgidinov.roiti.schoolgrading.bootstrap;

import com.georgidinov.roiti.schoolgrading.domain.Mark;
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
import java.util.List;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    //== fields ==
    private final MarkRepository markRepository;

    //== constructors ==
    @Autowired
    public DataLoader(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }


    //== public methods ==
    @Override
    public void run(String... args) throws Exception {
        List<Mark> marks = this.fetchData();
        this.processData(marks);
        //this.markRepository.saveAll(marks);
    }

    private List<Mark> fetchData() {
        Path path = FileSystems.getDefault().getPath("DomainData/marks.csv");
        List<Mark> marks = new ArrayList<>();
        try {
            marks = new CsvToBeanBuilder<Mark>(new CSVReader(Files.newBufferedReader(path)))
                    .withType(Mark.class).build().parse();
            log.info("CSV file loaded successfully!");
            Mark container = marks.get(0);
            log.info("Student = {}", container.getStudent());
            log.info("Course = {}", container.getCourse());
            log.info("Mark = {}", container.getMark());
        } catch (IOException e) {
            System.out.println("Error reading csv file " + e.getMessage());
        }
        return marks;
    }


    private void processData(List<Mark> marks) {
        log.info("processData() called...");
        marks.forEach(this::createRelations);
        log.info("createRelations complete...");
    }

    private void createRelations(Mark mark) {

    }

}