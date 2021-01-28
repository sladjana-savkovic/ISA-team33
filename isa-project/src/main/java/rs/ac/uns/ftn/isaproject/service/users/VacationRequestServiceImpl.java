package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.model.enums.VacationRequestStatus;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.VacationRequest;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.users.VacationRequestRepository;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {

	private VacationRequestRepository vacationRepository;
	private DoctorRepository doctorRepository;
	
	@Autowired
	public VacationRequestServiceImpl(VacationRequestRepository vacationRepository, DoctorRepository doctorRepository) {
		this.vacationRepository = vacationRepository;
		this.doctorRepository = doctorRepository;
	}

	@Override
	public void Add(AddVacationRequestDTO vacationRequestDTO) {
		VacationRequest vacationRequest = new VacationRequest();
		Doctor doctor = doctorRepository.getOne(vacationRequestDTO.doctorId);
		
		vacationRequest.setStartDate(vacationRequestDTO.startDate);
		vacationRequest.setEndDate(vacationRequestDTO.endDate);
		vacationRequest.setDoctor(doctor);
		vacationRequest.setStatus(VacationRequestStatus.Created);
		
		vacationRepository.save(vacationRequest);
	}
}
