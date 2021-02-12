package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.DrugReservationDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Patient;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityPharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugReservationRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class DrugReservationServiceImpl implements DrugReservationService {

	private DrugReservationRepository drugReservationRepository;
	private DoctorRepository doctorRepository;
	private PatientRepository patientRepository;
	private DrugQuantityPharmacyRepository drugQuantityPharmacyRepository;;

	@Autowired
	public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository,
			DoctorRepository doctorRepository, PatientRepository patientRepository,
			DrugQuantityPharmacyRepository drugQuantityPharmacyRepository) {
		this.drugReservationRepository = drugReservationRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.drugQuantityPharmacyRepository = drugQuantityPharmacyRepository;

	}

	@Override
	public DrugReservation searchOne(int id, int doctorId) {
		Collection<Pharmacy> doctorPharmacy = new ArrayList<>(doctorRepository.getOne(doctorId).getPharmacies());
		DrugReservation drugReservation = drugReservationRepository.getOne(id);

		if(drugReservation.getDateLimit().isAfter(LocalDateTime.now().plus(Period.ofDays(1))) && !drugReservation.isDone()) {
			for(Pharmacy p:doctorPharmacy) {
				if(p.getId() == drugReservation.getPharmacy().getId())
					return drugReservation;
			}
		}
		return null;
	}

	@Override
	public DrugReservation confirmReservation(int id) {
		DrugReservation drugReservation = drugReservationRepository.getOne(id);
		drugReservation.setDone(true);
		return drugReservationRepository.save(drugReservation);
	}

	@Override
	public DrugReservation createReservation(DrugReservationDTO drugReservastionDTO) throws Exception {
		DrugReservation drugReservation = new DrugReservation();
		drugReservation.setDateLimit(drugReservastionDTO.dateLimit);
		drugReservation.setDone(false);
		DrugQuantityPharmacy d = drugQuantityPharmacyRepository
				.findByPharmacyIdAndDrugId(drugReservastionDTO.pharmacyId, drugReservastionDTO.drugId);

		if (d.getQuantity() < 1)
			throw new Exception();
		drugReservation.setDrug(d.getDrug());
		Patient p = patientRepository.findById(drugReservastionDTO.patientId).get();
		drugReservation.setPatient(p);
		drugReservation.setPharmacy(d.getPharmacy());
		drugReservationRepository.saveAndFlush(drugReservation);
		d.setQuantity(d.getQuantity() - 1);
		drugQuantityPharmacyRepository.save(d);
		return drugReservation;

	}

	@Override
	public void cancelReservation(int id) throws Exception {

		DrugReservation d = drugReservationRepository.findById(id).get();

		if (d.isDone() || !d.getDateLimit().isAfter(LocalDateTime.now().plus(Period.ofDays(1))))
			throw new BadRequestException("The appointment cannot be cancelled.");

		Patient p = patientRepository.findById(d.getPatient().getId()).get();
		p.setPenalty(p.getPenalty() + 1);
		patientRepository.save(p);

		DrugQuantityPharmacy dq = drugQuantityPharmacyRepository.findByPharmacyIdAndDrugId(d.getPharmacy().getId(),
				d.getDrug().getId());
		dq.setQuantity(dq.getQuantity() + 1);
		drugQuantityPharmacyRepository.save(dq);
		drugReservationRepository.delete(d);
	}
	
	@Override
	public Collection<DrugReservation> findUnfinishedReservationsByPatient(int patientId) {
		return drugReservationRepository.findByPatientIdAndIsDone(patientId, false);
		
	}

	@Override
	public Collection<DrugReservation> findFinishedReservationsByPatient(int patientId) {
		return drugReservationRepository.findByPatientIdAndIsDone(patientId, true);
		
	}
}
