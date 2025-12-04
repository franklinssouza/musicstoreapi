package store.api.config.exceptions;

import org.springframework.http.HttpStatus;

public class StoreException extends Exception {

	private static final long serialVersionUID = 1L;

	private Boolean email;
	private HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

	public StoreException(String m, HttpStatus statusCode, Boolean email) {
		super(m);
		this.email = email;
		this.statusCode = statusCode;
	}

	public StoreException(String m) {
		super(m);
		this.email = false;
	}

	public StoreException(String m, Throwable e) {
		super(m,e);
		this.email = true;
	}
	public StoreException(String m, Throwable e, HttpStatus statusCode) {
		super(m,e);
		this.email = true;
		this.statusCode = statusCode;
	}
	public StoreException(String m, HttpStatus statusCode) {
		super(m);
		this.email = false;
		this.statusCode = statusCode;
	}
	public Boolean getEmail() {
		return email;
	}
	public void setEmail(Boolean email) {
		this.email = email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
}
