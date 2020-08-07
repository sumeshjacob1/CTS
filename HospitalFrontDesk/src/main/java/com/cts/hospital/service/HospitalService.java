package com.cts.hospital.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.hospital.config.HospitalPropperties;
import com.cts.hospital.config.HospitalPropperties.Hospital;
import com.cts.hospital.config.HospitalPropperties.Hospital.Bed;
import com.cts.hospital.config.HospitalPropperties.Hospital.Specialist;
import com.cts.hospital.exception.BusinessValidationException;
import com.cts.hospital.exception.RecordNotFoundException;
import com.cts.hospital.model.AppointmentResponse;
import com.cts.hospital.repository.HospitalRepository;;

/**
 * 
 * @author Sumesh Jacob (327723)
 *
 */
@Service
public class HospitalService implements HospitalRepository {

	private static final String OUTPUT_FORMAT_XML = "xml";

	private static final String OUTPUT_FORMAT_JSON = "json";

	Logger logger = LoggerFactory.getLogger(HospitalService.class);

	@Autowired
	HospitalPropperties props;

	@Value("${specialist.by.hospital.specialist}")
	String pathURL;

	@Cacheable(value = "specialists", key = "{#hospitalName,#specialistType}", condition = "#result==null or #result.size()==0")
	public List<Specialist> getSpecialistByHospital(String hospitalName, String specialistType)
			throws BusinessValidationException, RecordNotFoundException {

		logger.info("Getting specialist type for hospital ");

		validateParams(hospitalName, specialistType);

		List<Specialist> specialists = new ArrayList<Specialist>();
		for (Hospital hospital : props.getHospitals()) {
			if (hospitalName.equals(hospital.getName())) {
				for (Specialist specialist : hospital.getSpecialists()) {
					if (specialistType.equals(specialist.getType())) {
						specialists.add(specialist);
					}
				}

				// If there is hospital name match we can break
				break;
			}
		}

		if (specialists.size() > 0) {
			return specialists;
		} else {
			throw new RecordNotFoundException(
					"No specialist of type : " + specialistType + " not found for hospital : " + hospitalName);
		}

	}

	@Cacheable(value = "appointments", key = "{#hospitalName,#specialistName,#day,#patientName}", condition = "#result==null")
	public AppointmentResponse getAppointment(String hospitalName, String specialistName, String day,
			String patientName) throws BusinessValidationException, RecordNotFoundException {

		logger.info("Getting apointments");

		validateParams(hospitalName, specialistName, day, patientName);
		AppointmentResponse response = null;

		for (Hospital hospital : props.getHospitals()) {
			if (hospitalName.equals(hospital.getName())) {
				for (Specialist specialist : hospital.getSpecialists()) {
					if (specialistName.equals(specialist.getName()) && specialist.getAvailableday() != null
							&& specialist.getAvailableday().contains(day)) {

						response = new AppointmentResponse();
						response.setAppointmentDay(day);
						response.setSpecialistName(specialistName);
						response.setAppointmentTime(specialist.getAvailableTime());
						response.setPatientName(patientName);

						break;

					}
				}

				// If there is hospital name match we can break
				break;
			}
		}

		if (response != null) {
			return response;
		} else {
			throw new RecordNotFoundException(
					"Dear " + patientName + ", appointment is not avaiable for hospital : " + hospitalName
							+ " for specialist: " + specialistName + " of type : " + specialistName + " on  " + day);
		}
	}

	@Override
	public String getBedsForAdmission(String hospitalName) throws BusinessValidationException {

		logger.info("Getting available beds for admission in hospital");

		validateParams(hospitalName);
		String response = "Number of Beds Available is = ";
		int availableBedCounter = 0;
		for (Hospital hospital : props.getHospitals()) {
			if (hospitalName.equals(hospital.getName())) {

				for (Bed bed : hospital.getBeds()) {
					if ("DISCHARGE".equals(bed.getPatientStatus())) {
						availableBedCounter++;
					}
				}

				response = response + availableBedCounter;

				// If there is hospital name match we can break
				break;
			}
		}

		if (availableBedCounter == 0) {
			throw new BusinessValidationException("No beds available for admission");
		}
		return response;
	}

	@Override
	public ResponseEntity<?> testSpecialistEndPoint(String port, String environment, String outputFormat,
			String hospitalName, String specialistType) throws BusinessValidationException, RecordNotFoundException {

		logger.info("Testing Specialist rest End point");

		validateParams(port, environment, outputFormat, hospitalName, specialistType);

		StringBuilder urlBuilder = new StringBuilder("http://");
		urlBuilder.append(environment).append(":").append(port).append("/system");

		String pathParameters = pathURL.replace("{name}", hospitalName);
		pathParameters = pathParameters.replace("{type}", specialistType);
		urlBuilder.append(pathParameters);

		logger.info("URL to be invoked : " + urlBuilder.toString());

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		if (OUTPUT_FORMAT_JSON.equals(outputFormat)) {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		} else if (OUTPUT_FORMAT_XML.equals(outputFormat)) {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		}

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<?> response = null;

		try {
			response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, entity, String.class);
		} catch (Exception e) {
			throw new RecordNotFoundException(e.getLocalizedMessage());
		}
		return response;
	}

	private void validateParams(String hospitalName, String specialistName, String day, String patientName)
			throws BusinessValidationException {

		if (hospitalName == null || hospitalName.isEmpty()) {
			throw new BusinessValidationException("Invalid hospital name");
		}

		if (specialistName == null || specialistName.isEmpty()) {
			throw new BusinessValidationException("Invalid specialist name");
		}

		if (day == null || day.isEmpty()) {
			throw new BusinessValidationException("Invalid day");
		}

		if (patientName == null || patientName.isEmpty()) {
			throw new BusinessValidationException("Invalid day");
		}

	}

	private void validateParams(String hospitalName, String specialistType) throws BusinessValidationException {

		if (hospitalName == null || hospitalName.isEmpty()) {
			throw new BusinessValidationException("Invalid hospital name");
		}

		if (specialistType == null || specialistType.isEmpty()) {
			throw new BusinessValidationException("Invalid specialist type");
		}
	}

	private void validateParams(String hospitalName) throws BusinessValidationException {

		if (hospitalName == null || hospitalName.isEmpty()) {
			throw new BusinessValidationException("Invalid hospital name");
		}
	}

	private void validateParams(String port, String environment, String outputFormat, String hospitalName,
			String specialistType) throws BusinessValidationException {

		if (hospitalName == null || hospitalName.isEmpty()) {
			throw new BusinessValidationException("Invalid hospital name");
		}

		if (specialistType == null || specialistType.isEmpty()) {
			throw new BusinessValidationException("Invalid specialist type");
		}

		if (port == null || port.isEmpty()) {
			throw new BusinessValidationException("Invalid port");
		} else {
			try {
				Integer.valueOf(port);
			} catch (NumberFormatException e) {
				throw new BusinessValidationException("Invalid port");
			}
		}

		if (environment == null || environment.isEmpty()) {
			throw new BusinessValidationException("Invalid environment");
		}

		if (!OUTPUT_FORMAT_JSON.equals(outputFormat) && !OUTPUT_FORMAT_XML.equals(outputFormat)) {
			throw new BusinessValidationException("Unsupported output format");
		}
	}
}