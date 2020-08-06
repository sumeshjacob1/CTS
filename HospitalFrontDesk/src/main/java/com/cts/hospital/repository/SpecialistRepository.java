package com.cts.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.hospital.model.SpcialistEntity;
 
@Repository
public interface SpecialistRepository
        extends JpaRepository<SpcialistEntity, Long> {
 
}
