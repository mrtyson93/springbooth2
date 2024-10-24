package com.example.springboot.repositories;

import com.example.springboot.models.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataEntityRepository extends JpaRepository<DataEntity, String> {
    // The first type parameter (DataEntity) is the entity type
    // The second type parameter (String) is the type of the entity's ID field
}