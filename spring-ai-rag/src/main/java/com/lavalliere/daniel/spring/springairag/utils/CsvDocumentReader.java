package com.lavalliere.daniel.spring.springairag.utils;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.core.io.Resource;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvDocumentReader  implements DocumentReader {
    private final Resource resource;
    private List<Document> documents;

    public CsvDocumentReader(Resource resource) {
        this.resource = resource;
    }

    @Override
    public List<Document> read() {
        if (documents == null) {
            documents = parseCsv();
        }
        return new ArrayList<>(documents); // Return copy for immutability
    }

    @Override
    public List<Document> get() {
        return read(); // Delegate to read() as per Supplier interface
    }

    private List<Document> parseCsv() {
        List<Document> docs = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            String[] headers = csvReader.readNext();

            String[] row;
            int rowNum = 1;
            while ((row = csvReader.readNext()) != null) {
                StringBuilder content = new StringBuilder();
                Map<String, Object> metadata = Map.of(
                    "source", resource.getFilename(),
                    "row", rowNum
                );

                for (int i = 0; i < Math.min(row.length, headers != null ? headers.length : row.length); i++) {
                    String header = headers != null && i < headers.length ? headers[i] : "col" + i;
                    content.append(header).append(": ").append(row[i]).append("\n");
                }

                docs.add(new Document(content.toString(), metadata));
                rowNum++;
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Failed to parse CSV: " + resource.getFilename(), e);
        }
        return docs;
    }
}
