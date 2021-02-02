package rs.ac.uns.ftn.isaproject.service.users;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.WorkingTimeDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.WorkingTime;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.users.WorkingTimeRepository;

@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {

	private WorkingTimeRepository workingTimeRepository;
	private DoctorRepository doctorRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public WorkingTimeServiceImpl(WorkingTimeRepository workingTimeRepository, DoctorRepository doctorRepository, PharmacyRepository pharmacyRepository) {
		this.workingTimeRepository = workingTimeRepository;
		this.doctorRepository = doctorRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public void add(WorkingTimeDTO workingTimeDTO) {
		WorkingTime workingTime = new WorkingTime();
		Doctor doctor = doctorRepository.getOne(workingTimeDTO.doctorId);
		Pharmacy pharmacy = pharmacyRepository.getOne(workingTimeDTO.pharmacyId);
		
		workingTime.setStartTime(workingTimeDTO.startTime);
		workingTime.setEndTime(workingTimeDTO.endTime);
		workingTime.setDoctor(doctor);
		workingTime.setPharmacy(pharmacy);
		
		workingTimeRepository.save(workingTime);
	}

	@Override
	public Collection<WorkingTime> findByPharmacyId(int id) {
		Collection<WorkingTime> workingTimes = workingTimeRepository.findByPharmacyId(id);
		Collection<WorkingTime> resultWorkingTimes = new ArrayList<WorkingTime>();
		for(WorkingTime w : workingTimes) {
			Doctor doctor = doctorRepository.getOne(w.getDoctor().getId());
			if(doctor.isIsDeleted()==false) {
				resultWorkingTimes.add(w);
			}
		}
		
		return resultWorkingTimes;
	}

	@Override
	public boolean isDoctorWorkInPharmacy(int id_pharmacy, int id_doctor, String start_time, String end_time) {
		WorkingTime workingTime = workingTimeRepository.findByPharmacyDoctorId(id_pharmacy, id_doctor);
		if((LocalTime.parse(start_time).isAfter(workingTime.getStartTime()) || LocalTime.parse(start_time).equals(workingTime.getStartTime()))  && (LocalTime.parse(end_time).isBefore(workingTime.getEndTime()) || LocalTime.parse(end_time).equals(workingTime.getEndTime()))) {
			return true;
		}
		return false;
	}
}
