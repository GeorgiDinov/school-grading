package com.georgidinov.roiti.schoolgrading.bootstrap;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        load();
    }

    private void load() {
        Path path = FileSystems.getDefault().getPath("DomainData/marks.csv");

        try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(path))) {
            List<String[]> records = csvReader.readAll();
            for (int i = 0; i < 5; i++) {
                System.out.println(Arrays.toString(records.get(i)));
            }
        } catch (IOException | CsvException e) {
            System.out.println("Error reading csv file " + e.getMessage());
        }

    }


}