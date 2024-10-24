package com.example.springboot.controllers;

import com.example.springboot.services.DataEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;


import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class UploadDataControllerTest {

    @Mock
    private DataEntityService dataEntityService;

    @InjectMocks
    private UploadDataController uploadDataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFile_Success() throws IOException {
        // Arrange
        String csvContent = "source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "ZIB,ZIB001,271636001,Polsslag regelmatig,The long description,01-01-2019,12-31-2020,1";
        MockMultipartFile file = new MockMultipartFile("csvfile", "test.csv", "text/csv", csvContent.getBytes());

        // Act
        ResponseEntity<?> response = uploadDataController.uploadFile(file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded and processed successfully", response.getBody());
        verify(dataEntityService, times(1)).saveAllDataEntities(anyList());
    }

    @Test
    void uploadFile_IoException() throws IOException {
        // Arrange
        MockMultipartFile file = mock(MockMultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException("Test IO Exception"));

        // Act
        ResponseEntity<?> response = uploadDataController.uploadFile(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((String) Objects.requireNonNull(response.getBody())).startsWith("Error uploading file:"));
        verify(dataEntityService, never()).saveAllDataEntities(anyList());
    }

    @Test
    void uploadFile_GeneralException() throws IOException {
        // Arrange
        String csvContent = "source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "ZIB,ZIB001,271636001,Polsslag regelmatig,The long description,invalid-date,12-31-2020,1";
        MockMultipartFile file = new MockMultipartFile("csvfile", "test.csv", "text/csv", csvContent.getBytes());

        // Act
        ResponseEntity<?> response = uploadDataController.uploadFile(file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(((String) Objects.requireNonNull(response.getBody())).startsWith("Error processing file:"));
        verify(dataEntityService, never()).saveAllDataEntities(anyList());
    }
}

