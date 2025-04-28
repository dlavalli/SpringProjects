package com.lavalliere.daniel.spring.spring6restmvc.services;

import com.lavalliere.daniel.spring.spring6restmvc.model.BeerCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCSV(File csvFile) {
        try {
            // https://opencsv.sourceforge.net/#quick_start
            return new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                .withType(BeerCSVRecord.class)
                .build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}