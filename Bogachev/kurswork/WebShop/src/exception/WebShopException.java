package exception;

public class WebShopException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WebShopException(String error) {
		super(error);
	}
	
}
