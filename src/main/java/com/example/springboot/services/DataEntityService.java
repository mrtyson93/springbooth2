package com.example.springboot.services;

import com.example.springboot.models.DataEntity;
import com.example.springboot.repositories.DataEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataEntityService {

    private final DataEntityRepository dataEntityRepository;

    @Autowired
    public DataEntityService(DataEntityRepository dataEntityRepository) {
        this.dataEntityRepository = dataEntityRepository;
    }

    public DataEntity getDataEntityByCode(String code) {
        return dataEntityRepository.findById(code).orElse(null);
    }

    public List<DataEntity> getAllDataEntities() {
        return dataEntityRepository.findAll();
    }

    @Transactional
    public void deleteAllDataEntities() {
        dataEntityRepository.deleteAll();
    }

    @Transactional
    public List<DataEntity> saveAllDataEntities(List<DataEntity> dataEntities) {
        return dataEntityRepository.saveAll(dataEntities);
    }
}

