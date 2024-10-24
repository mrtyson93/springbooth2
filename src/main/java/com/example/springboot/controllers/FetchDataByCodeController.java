package com.example.springboot.controllers;

import com.example.springboot.models.DataEntity;
import com.example.springboot.services.DataEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class FetchDataByCodeController {

    private final DataEntityService dataEntityService;

    @Autowired
    public FetchDataByCodeController(DataEntityService dataEntityService) {
        this.dataEntityService = dataEntityService;
    }

    private static final Logger logger = LoggerFactory.getLogger(FetchDataByCodeController.class);

    @GetMapping("/data/{code}")
    @Operation(summary = "Get a data entity by its code", description = "Retrieves a data entity based on the provided code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the data entity",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataEntity.class))),
            @ApiResponse(responseCode = "404", description = "Data entity not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> getDataByCode(
            @Parameter(description = "Code of the data entity to be retrieved") @PathVariable String code) {
        logger.info("Fetching data entity by code");
        try {
            DataEntity dataEntity = dataEntityService.getDataEntityByCode(code);
            if (dataEntity != null) {
                return ResponseEntity.ok(dataEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Exception fetching data entity", e);
            return ResponseEntity.internalServerError().body("Error fetching data by code");
        }
    }
}
