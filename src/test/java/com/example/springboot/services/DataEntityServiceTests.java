package com.example.springboot.services;

import com.example.springboot.models.DataEntity;
import com.example.springboot.repositories.DataEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataEntityServiceTests {

    @Mock
    private DataEntityRepository dataEntityRepository;

    @InjectMocks
    private DataEntityService dataEntityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDataEntityByCode_ExistingCode() {
        // Arrange
        String code = "TEST001";
        DataEntity expectedEntity = new DataEntity();
        expectedEntity.setCode(code);
        when(dataEntityRepository.findById(code)).thenReturn(Optional.of(expectedEntity));

        // Act
        DataEntity result = dataEntityService.getDataEntityByCode(code);

        // Assert
        assertNotNull(result);
        assertEquals(code, result.getCode());
        verify(dataEntityRepository, times(1)).findById(code);
    }

    @Test
    void getDataEntityByCode_NonExistingCode() {
        // Arrange
        String code = "NONEXISTENT";
        when(dataEntityRepository.findById(code)).thenReturn(Optional.empty());

        // Act
        DataEntity result = dataEntityService.getDataEntityByCode(code);

        // Assert
        assertNull(result);
        verify(dataEntityRepository, times(1)).findById(code);
    }

    @Test
    void getAllDataEntities() {
        // Arrange
        List<DataEntity> expectedEntities = Arrays.asList(new DataEntity(), new DataEntity());
        when(dataEntityRepository.findAll()).thenReturn(expectedEntities);

        // Act
        List<DataEntity> result = dataEntityService.getAllDataEntities();

        // Assert
        assertEquals(expectedEntities.size(), result.size());
        verify(dataEntityRepository, times(1)).findAll();
    }

    @Test
    void deleteAllDataEntities() {
        // Act
        dataEntityService.deleteAllDataEntities();

        // Assert
        verify(dataEntityRepository, times(1)).deleteAll();
    }

    @Test
    void saveAllDataEntities() {
        // Arrange
        List<DataEntity> entitiesToSave = Arrays.asList(new DataEntity(), new DataEntity());
        when(dataEntityRepository.saveAll(entitiesToSave)).thenReturn(entitiesToSave);

        // Act
        List<DataEntity> result = dataEntityService.saveAllDataEntities(entitiesToSave);

        // Assert
        assertEquals(entitiesToSave.size(), result.size());
        verify(dataEntityRepository, times(1)).saveAll(entitiesToSave);
    }
}
