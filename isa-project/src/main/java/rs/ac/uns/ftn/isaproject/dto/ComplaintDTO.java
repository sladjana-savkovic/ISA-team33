package rs.ac.uns.ftn.isaproject.dto;

public class ComplaintDTO {

	public int complaintId;
	public String content;
	public int patientId;
	public String patientNameAndSurname;
	public String patientEmail;
	public int subjectId;  //id doktora ili apoteke
	public String subjectName; //ime doktora ili apoteke
	public String subjectType; //Dermatologist, Pharmacist, Pharmacy
	
	public ComplaintDTO() { }
}
