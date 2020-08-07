package com.cts.hospital.model;

/**
 * 
 * @author Sumesh Jacob (327723)
 *
 */
public class AppointmentResponse {

	String specialistName;
	String patientName;
	String appointmentDay;
	String appointmentTime;

	public String getSpecialistName() {
		return specialistName;
	}

	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getAppointmentDay() {
		return appointmentDay;
	}

	public void setAppointmentDay(String appointmentDay) {
		this.appointmentDay = appointmentDay;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appointmentDay == null) ? 0 : appointmentDay.hashCode());
		result = prime * result + ((appointmentTime == null) ? 0 : appointmentTime.hashCode());
		result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
		result = prime * result + ((specialistName == null) ? 0 : specialistName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppointmentResponse other = (AppointmentResponse) obj;
		if (appointmentDay == null) {
			if (other.appointmentDay != null)
				return false;
		} else if (!appointmentDay.equals(other.appointmentDay))
			return false;
		if (appointmentTime == null) {
			if (other.appointmentTime != null)
				return false;
		} else if (!appointmentTime.equals(other.appointmentTime))
			return false;
		if (patientName == null) {
			if (other.patientName != null)
				return false;
		} else if (!patientName.equals(other.patientName))
			return false;
		if (specialistName == null) {
			if (other.specialistName != null)
				return false;
		} else if (!specialistName.equals(other.specialistName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppointmentResponse [specialistName=" + specialistName + ", patientName=" + patientName
				+ ", appointmentDay=" + appointmentDay + ", appointmentTime=" + appointmentTime + "]";
	}

}
