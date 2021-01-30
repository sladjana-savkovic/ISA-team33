package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public class ViewSearchedDoctorMapper {

	public static Collection<ViewSearchedDoctorDTO> toViewSearchedDoctorDTODrugDTOs(Collection<Doctor> doctors){
		
		Collection<ViewSearchedDoctorDTO> doctorDTOs = new ArrayList<>();
		
		for(Doctor d:doctors) {
			Collection<PharmacyDTO> pharmacies = PharmacyMapper.toPharmacyDTOs(d.getPharmacies());
			doctorDTOs.add(new ViewSearchedDoctorDTO(d.getId(), d.getName(), d.getSurname(), d.getAverageGrade(), pharmacies, d.getTypeOfDoctor()));
		}
		
		return doctorDTOs;
	}
	
}
