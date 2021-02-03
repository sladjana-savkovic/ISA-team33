package rs.ac.uns.ftn.isaproject.service.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.AddVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.model.enums.VacationRequestStatus;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.VacationRequest;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.users.VacationRequestRepository;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {

	private VacationRequestRepository vacationRepository;
	private DoctorRepository doctorRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public VacationRequestServiceImpl(VacationRequestRepository vacationRepository, DoctorRepository doctorRepository,PharmacyRepository pharmacyRepository) {
		this.vacationRepository = vacationRepository;
		this.doctorRepository = doctorRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public void add(AddVacationRequestDTO vacationRequestDTO) {
		VacationRequest vacationRequest = new VacationRequest();
		Doctor doctor = doctorRepository.getOne(vacationRequestDTO.doctorId);
		Pharmacy pharmacy = pharmacyRepository.getOne(vacationRequestDTO.pharmacyId);
		
		vacationRequest.setStartDate(vacationRequestDTO.startDate);
		vacationRequest.setEndDate(vacationRequestDTO.endDate);
		vacationRequest.setDoctor(doctor);
		vacationRequest.setStatus(VacationRequestStatus.Created);
		vacationRequest.setPharmacy(pharmacy);
		
		vacationRepository.save(vacationRequest);
	}

	@Override
	public void acceptRequest(int id) {
		VacationRequest vacationRequest = vacationRepository.getOne(id);
		vacationRequest.setStatus(VacationRequestStatus.Confirmed);
		
		vacationRepository.save(vacationRequest);
	}

	@Override
	public void rejectRequest(int id, String reason) {
		VacationRequest vacationRequest = vacationRepository.getOne(id);
		vacationRequest.setStatus(VacationRequestStatus.Rejected);
		vacationRequest.setReasonForRejection(reason);
		
		vacationRepository.save(vacationRequest);
	}

	@Override
	public Collection<VacationRequest> findCreatedByPharmacyId(int id) {
		Collection<VacationRequest> requests = vacationRepository.findByPharmacyId(id);
		Collection<VacationRequest> createdRequests = new ArrayList<VacationRequest>();
		for(VacationRequest v: requests) {
			if(v.getStatus().equals(VacationRequestStatus.Created)) {
				createdRequests.add(v);
			}
		}
		return createdRequests;
	}

	@Override
	public boolean isDoctorOnVacation(int doctorId, int pharmacyId, LocalDate date) {
		Collection<VacationRequest> vacationRequests = vacationRepository.findByDoctorPharmacyId(pharmacyId, doctorId);
		
		for(VacationRequest r : vacationRequests) {
			if(r.getStatus().equals(VacationRequestStatus.Confirmed) && date.isAfter(r.getStartDate()) && date.isBefore(r.getEndDate())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Doctor findDoctorById(int id) {
		return vacationRepository.findDoctorById(id);
	}
}
