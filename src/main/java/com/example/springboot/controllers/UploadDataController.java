package com.example.springboot.controllers;

import com.example.springboot.models.DataEntity;
import com.example.springboot.services.DataEntityService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UploadDataController {

    private final DataEntityService dataEntityService;

    @Autowired
    public UploadDataController(DataEntityService dataEntityService) {
        this.dataEntityService = dataEntityService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UploadDataController.class);

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("csvfile") MultipartFile file) {
        List<DataEntity> dataEntities = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        // Parse CSV
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            // Create Data Entity Objects
            for (CSVRecord csvRecord : csvParser) {
                DataEntity entity = new DataEntity();
                entity.setSource(csvRecord.get("source"));
                entity.setCodeListCode(csvRecord.get("codeListCode"));
                entity.setCode(csvRecord.get("code"));
                entity.setDisplayValue(csvRecord.get("displayValue"));
                entity.setLongDescription(csvRecord.get("longDescription"));
                entity.setFromDate(LocalDate.parse(csvRecord.get("fromDate"), dateFormatter));

                String toDateStr = csvRecord.get("toDate");
                if (toDateStr != null && !toDateStr.isEmpty()) {
                    entity.setToDate(LocalDate.parse(toDateStr, dateFormatter));
                }

                String sortingPriorityStr = csvRecord.get("sortingPriority");
                if (sortingPriorityStr != null && !sortingPriorityStr.isEmpty()) {
                    entity.setSortingPriority(Integer.parseInt(sortingPriorityStr));
                }

                dataEntities.add(entity);
                logger.info("Created DataEntity: {}", entity);
            }

            // Save objects to database
            dataEntityService.saveAllDataEntities(dataEntities);
            return ResponseEntity.ok().body("File uploaded and processed successfully");

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing file: " + e.getMessage());
        }

    }
}
