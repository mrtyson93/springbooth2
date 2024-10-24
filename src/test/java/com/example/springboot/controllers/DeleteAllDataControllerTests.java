package com.example.springboot.controllers;

import com.example.springboot.services.DataEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeleteAllDataControllerTests {

    @Mock
    private DataEntityService dataEntityService;

    @InjectMocks
    private DeleteAllDataController deleteAllDataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteAllData_Success() {
        // Arrange
        doNothing().when(dataEntityService).deleteAllDataEntities();

        // Act
        ResponseEntity<Void> response = deleteAllDataController.deleteAllData();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(dataEntityService, times(1)).deleteAllDataEntities();
    }

    @Test
    void deleteAllData_Exception() {
        // Arrange
        doThrow(new RuntimeException("Delete failed")).when(dataEntityService).deleteAllDataEntities();

        // Act
        ResponseEntity<Void> response = deleteAllDataController.deleteAllData();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(dataEntityService, times(1)).deleteAllDataEntities();
    }
}
