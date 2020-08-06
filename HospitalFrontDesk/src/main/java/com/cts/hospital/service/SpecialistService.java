package com.cts.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cts.hospital.exception.RecordNotFoundException;
import com.cts.hospital.model.SpcialistEntity;
import com.cts.hospital.repository.SpecialistRepository;

@Service
@CacheConfig(cacheNames = { "specialist" })
public class SpecialistService {

	@Autowired
	SpecialistRepository repository;

	public List<SpcialistEntity> getAllSpecialist() {
		List<SpcialistEntity> specialists = repository.findAll();

		if (specialists.size() > 0) {
			return specialists;
		} else {
			return new ArrayList<SpcialistEntity>();
		}
	}

	@Cacheable(value = "cacheSpecialist", key = "#id")
	public SpcialistEntity getSpecialistById(Long id) throws RecordNotFoundException {
		Optional<SpcialistEntity> specialist = repository.findById(id);

		if (specialist.isPresent()) {
			return specialist.get();
		} else {
			throw new RecordNotFoundException("No specialist record exist for given id");
		}
	}

	public SpcialistEntity createOrUpdateSpecialist(SpcialistEntity entity) throws RecordNotFoundException {
		Optional<SpcialistEntity> specialist = repository.findById(entity.getId());

		if (specialist.isPresent()) {
			SpcialistEntity newEntity = specialist.get();
			newEntity.setType(entity.getType());
			newEntity.setName(entity.getName());
			newEntity.setAvailableday(entity.getAvailableday());
			newEntity.setAvailableTime(entity.getAvailableTime());
			newEntity.setIsAvailable(entity.getIsAvailable());
			newEntity.setHospitalId(entity.getHospitalId());

			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			entity = repository.save(entity);

			return entity;
		}
	}

	public void deleteSpecialistById(Long id) throws RecordNotFoundException {
		Optional<SpcialistEntity> specialist = repository.findById(id);

		if (specialist.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No specialist record exist for given id");
		}
	}
}