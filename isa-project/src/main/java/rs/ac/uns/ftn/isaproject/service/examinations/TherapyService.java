package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AddTherapyDTO;

public interface TherapyService {

	void add(Collection<AddTherapyDTO> therapyDTOs);
}
