package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

public class PharmacyMapper {

	public static Collection<PharmacyDTO> toPharmacyDTOs(Collection<Pharmacy> pharmacies){
		
		Collection<PharmacyDTO> phamracyDTOs = new ArrayList<>();
		
		for(Pharmacy p:pharmacies) {
			Collection<AppointmentDTO> appointmentDTOs = AppointmentMapper.toAppointmentDTOs(p.getAppointments());
			Collection<DoctorDTO> doctorDTOs = DoctorMapper.toDoctoryDTOs(p.getDoctors());
			phamracyDTOs.add(new PharmacyDTO(p.getId(), p.getName(), p.getAverageGrade(), p.getAddress(), p.getCity().getId(), p.getCity().getName(), p.getCity().getCountry().getName(), appointmentDTOs, doctorDTOs, p.getLatitude(), p.getLongitude(), p.getpharmacistPrice()));
		}
		
		return phamracyDTOs;
	}
}
