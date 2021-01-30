package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorPharmacyDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public class DoctorMapper {

	public static DoctorDTO toDoctorDTO(Doctor doctor) {
		String typeOfDoctor = "Dermatolog";
		if(doctor.getTypeOfDoctor() == TypeOfDoctor.Pharmacist) {
			typeOfDoctor = "Farmaceut";
		}
		
		return new DoctorDTO(doctor.getId(),doctor.getName(),doctor.getSurname(),doctor.getDateOfBirth(),doctor.getEmail(),doctor.getPassword(),
						     doctor.getAddress(), doctor.getCity().getId(),doctor.getCity().getName(),doctor.getCity().getCountry().getId(),
						     doctor.getCity().getCountry().getName(), doctor.getAverageGrade(),typeOfDoctor);
	}
	
	public static Collection<DoctorDTO> toDoctoryDTOs(Collection<Doctor> doctors){
		Collection<DoctorDTO> doctorDTOs = new ArrayList<>();
		for(Doctor d:doctors) {
			doctorDTOs.add(new DoctorDTO(d.getId(), d.getName(), d.getSurname(), d.getDateOfBirth(), d.getEmail(), d.getPassword(), d.getAddress(), 
							d.getCity().getId(), d.getCity().getName(),d.getCity().getCountry().getId(), d.getCity().getCountry().getName(), 
							d.getAverageGrade(), d.getTypeOfDoctor().toString()));
		}
		return doctorDTOs;
	}
	
	public static Collection<DoctorPharmacyDTO> toDoctorPharmacyDTOs(Doctor doctor){
		Collection<Pharmacy> pharmacies = doctor.getPharmacies();
		Collection<DoctorPharmacyDTO> doctorPharmacyDTOs = new ArrayList<>();
		
		for(Pharmacy p:pharmacies) {
			doctorPharmacyDTOs.add(new DoctorPharmacyDTO(doctor.getId(), p.getId(), p.getName()));
		}
		
		return doctorPharmacyDTOs;
	}
}
