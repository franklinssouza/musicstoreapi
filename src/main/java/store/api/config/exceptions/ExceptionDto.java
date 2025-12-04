package store.api.config.exceptions;

import java.io.Serializable;

public class ExceptionDto  implements Serializable {
	private static final long serialVersionUID = 1L;
	String message;

    public ExceptionDto(String message) {
        this.message = message;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
