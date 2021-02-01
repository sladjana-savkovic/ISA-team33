package rs.ac.uns.ftn.isaproject.dto;

public class AppointmentEventDTO {

	public String title;
	public String start;
	public String end;
	public String url;
	
	public AppointmentEventDTO() {}

	public AppointmentEventDTO(String title, String start, String end, String url) {
		super();
		this.title = title;
		this.start = start;
		this.end = end;
		this.url = url;
	}
	
}
