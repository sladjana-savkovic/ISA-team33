package rs.ac.uns.ftn.isaproject.service.users;


public interface PatientService {
 
	void increasePenalty(int id);
	boolean checkAllergyOnDrug(int id, int drugId);
}
