package rs.ac.uns.ftn.isaproject.exceptions;

public class NotFoundException extends IsaProjectException {

	private static final long serialVersionUID = -2923815700275814680L;

	public NotFoundException() {
        super();
    }
	
	public NotFoundException(String message) {
        super(message);
    }
}
