package com.example.springboot.controllers;

import com.example.springboot.services.DataEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeleteAllDataController {

    private final DataEntityService dataEntityService;

    @Autowired
    public DeleteAllDataController(DataEntityService dataEntityService) {
        this.dataEntityService = dataEntityService;
    }

    private static final Logger logger = LoggerFactory.getLogger(DeleteAllDataController.class);

    @DeleteMapping("/data")
    @Operation(summary = "Delete all data entities", description = "Deletes all data entities from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All data entities successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred during deletion")
    })
    public ResponseEntity<Void> deleteAllData() {
        logger.info("Attempting to delete all data entities");
        try {
            dataEntityService.deleteAllDataEntities();
            logger.info("Successfully deleted all data entities");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting all data entities", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
