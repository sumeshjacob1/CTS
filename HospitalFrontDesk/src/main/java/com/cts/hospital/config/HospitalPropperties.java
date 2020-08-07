package com.cts.hospital.config;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 
 * @author Sumesh Jacob (327723)
 *
 */
@Component
@ConfigurationProperties
@PropertySource("classpath:specialist.properties")
@Validated
public class HospitalPropperties {

	@NotEmpty
	@NotNull
	List<Hospital> hospitals = new ArrayList<>();

	public List<Hospital> getHospitals() {
		return hospitals;
	}

	public void setHospitals(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

	@Override
	public String toString() {
		return "HospitalPropperties [hospitals=" + hospitals + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hospitals == null) ? 0 : hospitals.hashCode());
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
		HospitalPropperties other = (HospitalPropperties) obj;

		if (hospitals == null) {
			if (other.hospitals != null)
				return false;
		} else if (!hospitals.equals(other.hospitals))
			return false;

		return true;
	}

	@XmlRootElement
	public static class Hospital {

		@NotEmpty
		@NotNull
		String name;

		@NotEmpty
		@NotNull
		String id;

		@NotEmpty
		@NotNull
		List<Specialist> specialists;

		@NotEmpty
		@NotNull
		List<Bed> beds;

		public String getHospitalName() {
			return getName();
		}

		public String getName() {
			return name;
		}

		public void setHospitalName(String hospitalName) {
			setName(hospitalName);
		}

		public void setName(String hospitalName) {
			this.name = hospitalName;
		}

		public String getId() {
			return id;
		}

		public void setId(String hospitalId) {
			this.id = hospitalId;
		}

		public List<Specialist> getSpecialists() {
			return specialists;
		}

		public void setSpecialists(List<Specialist> specilaists) {
			this.specialists = specilaists;
		}

		public List<Bed> getBeds() {
			return beds;
		}

		public void setBeds(List<Bed> beds) {
			this.beds = beds;
		}

		@Override
		public String toString() {
			return "Hospital [name=" + name + ", id=" + id + ", specialists=" + specialists + ", beds=" + beds + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((beds == null) ? 0 : beds.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((specialists == null) ? 0 : specialists.hashCode());
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
			Hospital other = (Hospital) obj;
			if (beds == null) {
				if (other.beds != null)
					return false;
			} else if (!beds.equals(other.beds))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (specialists == null) {
				if (other.specialists != null)
					return false;
			} else if (!specialists.equals(other.specialists))
				return false;
			return true;
		}

		@XmlRootElement
		public static class Specialist {

			@NotNull
			@NotEmpty
			String type;

			@NotNull
			@NotEmpty
			String name;

			String availableday;

			String availableTime;

			String isAvailable;

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getAvailableday() {
				return availableday;
			}

			public void setAvailableday(String availableday) {
				this.availableday = availableday;
			}

			public String getAvailableTime() {
				return availableTime;
			}

			public void setAvailableTime(String availableTime) {
				this.availableTime = availableTime;
			}

			public String getIsAvailable() {
				return isAvailable;
			}

			public void setIsAvailable(String isAvailable) {
				this.isAvailable = isAvailable;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((availableTime == null) ? 0 : availableTime.hashCode());
				result = prime * result + ((availableday == null) ? 0 : availableday.hashCode());
				result = prime * result + ((isAvailable == null) ? 0 : isAvailable.hashCode());
				result = prime * result + ((name == null) ? 0 : name.hashCode());
				result = prime * result + ((type == null) ? 0 : type.hashCode());
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
				Specialist other = (Specialist) obj;
				if (availableTime == null) {
					if (other.availableTime != null)
						return false;
				} else if (!availableTime.equals(other.availableTime))
					return false;
				if (availableday == null) {
					if (other.availableday != null)
						return false;
				} else if (!availableday.equals(other.availableday))
					return false;

				if (isAvailable == null) {
					if (other.isAvailable != null)
						return false;
				} else if (!isAvailable.equals(other.isAvailable))
					return false;
				if (name == null) {
					if (other.name != null)
						return false;
				} else if (!name.equals(other.name))
					return false;
				if (type == null) {
					if (other.type != null)
						return false;
				} else if (!type.equals(other.type))
					return false;
				return true;
			}

			@Override
			public String toString() {
				return "Specialist [type=" + type + ", name=" + name + ", availableday=" + availableday
						+ ", availableTime=" + availableTime + ", isAvailable=" + isAvailable + ", hospitalId=" + "]";
			}

		}

		public static class Bed {

			String patientStatus;

			public String getPatientStatus() {
				return patientStatus;
			}

			public void setPatientStatus(String patientStatus) {
				this.patientStatus = patientStatus;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((patientStatus == null) ? 0 : patientStatus.hashCode());
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
				Bed other = (Bed) obj;
				if (patientStatus == null) {
					if (other.patientStatus != null)
						return false;
				} else if (!patientStatus.equals(other.patientStatus))
					return false;
				return true;
			}

			@Override
			public String toString() {
				return "Bed [patientStatus=" + patientStatus + "]";
			}

		}
	}

}
