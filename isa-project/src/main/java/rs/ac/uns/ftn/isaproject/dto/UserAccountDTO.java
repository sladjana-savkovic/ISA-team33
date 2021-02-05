package rs.ac.uns.ftn.isaproject.dto;

public class UserAccountDTO {

	public Long id;
	public String username;
	public int userId;
	
	public UserAccountDTO() {}

	public UserAccountDTO(Long id, String username, int userId) {
		super();
		this.id = id;
		this.username = username;
		this.userId = userId;
	}
	
}
