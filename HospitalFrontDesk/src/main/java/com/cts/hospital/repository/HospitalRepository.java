package com.cts.hospital.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.hospital.config.HospitalPropperties.Hospital.Specialist;
import com.cts.hospital.exception.BusinessValidationException;
import com.cts.hospital.exception.RecordNotFoundException;
import com.cts.hospital.model.AppointmentResponse;

/**
 * 
 * @author Sumesh Jacob (327723)
 *
 */
public interface HospitalRepository {

	public List<Specialist> getSpecialistByHospital(String hospitalName, String specialistType)
			throws BusinessValidationException, RecordNotFoundException;

	public AppointmentResponse getAppointment(String hospitalName, String specialistName, String day,
			String patientName) throws BusinessValidationException, RecordNotFoundException;

	public String getBedsForAdmission(String hospitalName) throws BusinessValidationException, RecordNotFoundException;

	public ResponseEntity<?> testSpecialistEndPoint(String port, String environment, String outputFormat,
			String hospitalName, String specialistType) throws BusinessValidationException, RecordNotFoundException;

}
