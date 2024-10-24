package com.example.springboot.controllers;

import com.example.springboot.models.DataEntity;
import com.example.springboot.services.DataEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
public class FetchAllDataController {

    private final DataEntityService dataEntityService;

    @Autowired
    public FetchAllDataController(DataEntityService dataEntityService) {
        this.dataEntityService = dataEntityService;
    }

    private static final Logger logger = LoggerFactory.getLogger(FetchAllDataController.class);

    @GetMapping("/data")
    @Operation(summary = "Fetch all data entities", description = "Retrieves a list of all data entities from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of data entities",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataEntity.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Error fetching data: Internal server error")))
    })
    public ResponseEntity<?> getAllData() {
        try {
            logger.info("Fetching all data entities");
            List<DataEntity> allData = dataEntityService.getAllDataEntities();
            logger.info("Fetched {} data entities", allData.size());
            return ResponseEntity.ok(allData);
        } catch (Exception e) {
            logger.error("Exception fetching all data", e);
            return ResponseEntity.internalServerError().body("Error fetching data: " + e.getMessage());        }
    }
}
