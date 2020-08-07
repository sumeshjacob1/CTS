package com.cts.hospital.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("${root.path}")
public class HospitalController {

	Logger logger = LoggerFactory.getLogger(HospitalController.class);

	@Autowired
	HospitalService service;

	@Value("${test.endpoint}")
	String muthuHannah;

	@PostConstruct
	public void test() {
		logger.info(muthuHannah);
	}

	@GetMapping(value = "${specialist.by.hospital.specialist}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<HospitalPropperties.Hospital.Specialist>> getSpecialistsByHospital(
			@PathVariable("name") String hospitalName, @PathVariable("type") String specialistType)
			throws BusinessValidationException, RecordNotFoundException, Exception {

		logger.info("URL : ", muthuHannah);
		List<HospitalPropperties.Hospital.Specialist> list = service.getSpecialistByHospital(hospitalName,
				specialistType);

		return new ResponseEntity<List<HospitalPropperties.Hospital.Specialist>>(list, new HttpHeaders(),
				HttpStatus.OK);
	}

	@GetMapping(value = "${appointment.by.hospital.specialist.day}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable("hospitalName") String hospitalName,
			@PathVariable("specialistName") String specialistName, @PathVariable("day") String day,
			@PathVariable("patientName") String patientName)
			throws BusinessValidationException, RecordNotFoundException, Exception {

		AppointmentResponse response = service.getAppointment(hospitalName, specialistName, day, patientName);

		return new ResponseEntity<AppointmentResponse>(response, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "${hospital.availble.beds}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> getBedsForAdmission(@PathVariable("hospitalName") String hospitalName)
			throws BusinessValidationException, RecordNotFoundException {

		String response = service.getBedsForAdmission(hospitalName);

		return new ResponseEntity<String>(response, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "${test.endpoint}")
	public ResponseEntity<?> testSpecialistRestEndPoint(@PathVariable("port") String port,
			@PathVariable("environment") String environment, @PathVariable("type") String outputFormat,
			@PathVariable("hospitalName") String hospitalName, @PathVariable("specialistType") String specialistType)
			throws BusinessValidationException, RecordNotFoundException, Exception {

		logger.info("Testing Specialist rest End point");

		ResponseEntity<?> response = service.testSpecialistEndPoint(port, environment, outputFormat, hospitalName,
				specialistType);
		return response;
	}

}