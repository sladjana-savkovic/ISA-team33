package rs.ac.uns.ftn.isaproject.exceptions;

public class BadRequestException extends IsaProjectException {

	private static final long serialVersionUID = 5292887816659570482L;

	public BadRequestException() {
        super();
    }
	
	public BadRequestException(String message) {
        super(message);
    }

}
