package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.AddTherapyDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.model.examinations.Therapy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.repository.examinations.ExaminationReportRepository;
import rs.ac.uns.ftn.isaproject.repository.examinations.TherapyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;

@Service
public class TherapyServiceImpl implements TherapyService {

	private TherapyRepository therapyRepository;
	private DrugRepository drugRepository;
	private ExaminationReportRepository examinationReportRepository;
	
	@Autowired
	public TherapyServiceImpl(TherapyRepository therapyRepository, DrugRepository drugRepository,ExaminationReportRepository examinationReportRepository) {
		this.therapyRepository = therapyRepository;
		this.drugRepository = drugRepository;
		this.examinationReportRepository = examinationReportRepository;
	}

	@Override
	public void add(Collection<AddTherapyDTO> therapyDTOs) {
		Collection<Therapy> therapies = new ArrayList<>();
		
		for(AddTherapyDTO dto:therapyDTOs) {
			Drug drug = drugRepository.getOne(dto.drugId);
			ExaminationReport examinationReport = examinationReportRepository.getOne(dto.examinationReportId);
			Therapy therapy = new Therapy();
			
			therapy.setDrug(drug);
			therapy.setDuration(dto.duration);
			therapy.setExamination(examinationReport);
			
			therapies.add(therapy);
		}
		therapyRepository.saveAll(therapies);
	}
	
}
