package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public class DoctorMapper {

	public static DoctorDTO toDoctorDTO(Doctor doctor) {
		String typeOfDoctor = "Dermatolog";
		if(doctor.getTypeOfDoctor() == TypeOfDoctor.Pharmacist) {
			typeOfDoctor = "Farmaceut";
		}
		
		return new DoctorDTO(doctor.getId(),doctor.getName(),doctor.getSurname(),doctor.getDateOfBirth(),doctor.getEmail(),doctor.getPassword(),
						     doctor.getAddress(), doctor.getCity().getId(),doctor.getCity().getName(),
						     doctor.getCity().getCountry().getName(), doctor.getAverageGrade(),typeOfDoctor);
	}
}
