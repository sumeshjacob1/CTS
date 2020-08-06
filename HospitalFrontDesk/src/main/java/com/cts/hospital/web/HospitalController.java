package com.cts.hospital.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hospital.exception.RecordNotFoundException;
import com.cts.hospital.model.SpcialistEntity;
import com.cts.hospital.service.SpecialistService;

@RestController
@RequestMapping("${hospital.root.path}")
public class HospitalController {

	@Autowired
	SpecialistService service;

	@GetMapping(value = "${all.specialist}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<SpcialistEntity>> getAllSpecialist() {
		List<SpcialistEntity> list = service.getAllSpecialist();

		return new ResponseEntity<List<SpcialistEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "${specialist.by.id}{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<SpcialistEntity> getSpecialistById(@PathVariable("id") Long id)
			throws RecordNotFoundException {
		SpcialistEntity entity = service.getSpecialistById(id);

		return new ResponseEntity<SpcialistEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping(value = "${specialist.update}")
	public ResponseEntity<SpcialistEntity> createOrUpdateSpecialist(SpcialistEntity specialist)
			throws RecordNotFoundException {
		SpcialistEntity updated = service.createOrUpdateSpecialist(specialist);
		return new ResponseEntity<SpcialistEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("${specialist.delete}{id}")
	public HttpStatus deleteSpecialistById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteSpecialistById(id);
		return HttpStatus.FORBIDDEN;
	}

}