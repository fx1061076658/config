package com.eidlink.config.zk.exception;

public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -2196159509771060128L;
	
	private AppExceptionCode code = AppExceptionCode.DEFAULT;

	public AppRuntimeException() {
		super();
	}
	
	public AppRuntimeException(final String message) {
		super(message);
	}

	public AppRuntimeException(final Throwable cause) {
		super(cause);
	}

	public AppRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public AppRuntimeException(final AppExceptionCode code) {
		super();
		if (code != null) {
			this.code = code;
		}
	}
	
	public AppRuntimeException(final AppExceptionCode code, final String message) {
		super(message);
		if (code != null) {
			this.code = code;
		}
	}
	
	public AppRuntimeException(final AppExceptionCode code, final Throwable cause) {
		super(cause);
		if (code != null) {
			this.code = code;
		}
	}

	public AppRuntimeException(final AppExceptionCode code, final String message, final Throwable cause) {
		super(message, cause);
		if (code != null) {
			this.code = code;
		}
	}
	
	public String getCode() {
		return code.toString();
	}
	
	@Override
	public String toString() {
		Throwable cause = this.getCause();
		String message = this.getMessage();
		StringBuilder sb = new StringBuilder();
		sb.append(code.toString()).append(";\n");
		sb.append("ExceptionMessage: ");
		sb.append(message).append(";\n");
		sb.append("ExceptionCause: app runtime exception is ").append(cause);
		
		return sb.toString();
	}

}
