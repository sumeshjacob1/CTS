package com.cts.hospital.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

	Logger logger = LoggerFactory.getLogger(HospitalService.class);

	@Autowired
	HospitalPropperties props;

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
}