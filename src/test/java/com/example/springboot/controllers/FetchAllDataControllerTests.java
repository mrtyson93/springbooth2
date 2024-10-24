package com.example.springboot.controllers;

import com.example.springboot.models.DataEntity;
import com.example.springboot.services.DataEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FetchAllDataControllerTests {

    @Mock
    private DataEntityService dataEntityService;

    @InjectMocks
    private FetchAllDataController fetchAllDataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllData_Success() {
        DataEntity dataEntity1 = new DataEntity();
        dataEntity1.setCode("1");
        DataEntity dataEntity2 = new DataEntity();
        dataEntity1.setCode("2");
        // Arrange
        List<DataEntity> mockData = Arrays.asList(
                dataEntity1,
                dataEntity2
        );
        when(dataEntityService.getAllDataEntities()).thenReturn(mockData);

        // Act
        ResponseEntity<?> response = fetchAllDataController.getAllData();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(List.class, response.getBody());
        List<?> responseBody = (List<?>) response.getBody();
        assertEquals(2, responseBody.size());
        verify(dataEntityService, times(1)).getAllDataEntities();
    }

    @Test
    void getAllData_Exception() {
        // Arrange
        when(dataEntityService.getAllDataEntities()).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = fetchAllDataController.getAllData();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error fetching data: Database error", response.getBody());
        verify(dataEntityService, times(1)).getAllDataEntities();
    }
}
