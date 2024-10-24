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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FetchDataByCodeControllerTests {

    @Mock
    private DataEntityService dataEntityService;

    @InjectMocks
    private FetchDataByCodeController fetchDataByCodeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDataByCode_Success() {
        // Arrange
        String code = "TEST001";
        DataEntity mockEntity = new DataEntity();
        mockEntity.setCode(code);
        mockEntity.setDisplayValue("Test Display");
        when(dataEntityService.getDataEntityByCode(code)).thenReturn(mockEntity);

        // Act
        ResponseEntity<?> response = fetchDataByCodeController.getDataByCode(code);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(DataEntity.class, response.getBody());
        assertEquals(code, ((DataEntity) response.getBody()).getCode());
        verify(dataEntityService, times(1)).getDataEntityByCode(code);
    }

    @Test
    void getDataByCode_NotFound() {
        // Arrange
        String code = "NONEXISTENT";
        when(dataEntityService.getDataEntityByCode(code)).thenReturn(null);

        // Act
        ResponseEntity<?> response = fetchDataByCodeController.getDataByCode(code);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(dataEntityService, times(1)).getDataEntityByCode(code);
    }

    @Test
    void getDataByCode_Exception() {
        // Arrange
        String code = "ERROR001";
        when(dataEntityService.getDataEntityByCode(code)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = fetchDataByCodeController.getDataByCode(code);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error fetching data by code", response.getBody());
        verify(dataEntityService, times(1)).getDataEntityByCode(code);
    }
}

