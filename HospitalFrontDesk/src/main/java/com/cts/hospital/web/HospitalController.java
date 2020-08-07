package com.cts.hospital.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hospital.config.HospitalPropperties;
import com.cts.hospital.exception.BusinessValidationException;
import com.cts.hospital.exception.RecordNotFoundException;
import com.cts.hospital.model.AppointmentResponse;
import com.cts.hospital.service.HospitalService;

/**
 * 
 * @author Sumesh Jacob (327723)
 *
 */
@RestController
@RequestMapping("${rootPath}")
public class HospitalController {

	@Autowired
	HospitalService service;

	@GetMapping(value = "${specialistByHospitalNameAndSpecialistType}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<HospitalPropperties.Hospital.Specialist>> getSpecialistsByHospital(
			@PathVariable("name") String hospitalName, @PathVariable("type") String specialistType)
			throws BusinessValidationException, RecordNotFoundException {

		List<HospitalPropperties.Hospital.Specialist> list = service.getSpecialistByHospital(hospitalName,
				specialistType);

		return new ResponseEntity<List<HospitalPropperties.Hospital.Specialist>>(list, new HttpHeaders(),
				HttpStatus.OK);
	}

	@GetMapping(value = "${getAppointment}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable("hospitalName") String hospitalName,
			@PathVariable("specialistName") String specialistName, @PathVariable("day") String day,
			@PathVariable("patientName") String patientName)
			throws BusinessValidationException, RecordNotFoundException {

		AppointmentResponse response = service.getAppointment(hospitalName, specialistName, day, patientName);

		return new ResponseEntity<AppointmentResponse>(response, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "${getAvailableBeds}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> getBedsForAdmission(@PathVariable("hospitalName") String hospitalName)
			throws BusinessValidationException, RecordNotFoundException {

		String response = service.getBedsForAdmission(hospitalName);

		return new ResponseEntity<String>(response, new HttpHeaders(), HttpStatus.OK);
	}

}