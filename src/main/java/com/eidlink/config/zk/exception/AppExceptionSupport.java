package com.eidlink.config.zk.exception;

public final class AppExceptionSupport {
	
	private AppExceptionSupport(){};
	
	public static AppRuntimeException createAppRuntimeException(final AppExceptionCode code){
		return new AppRuntimeException(code);
	}
	
	public static AppRuntimeException createAppRuntimeException(final AppExceptionCode code, final String message){
		return new AppRuntimeException(code, message);
	}
	
	public static AppRuntimeException createAppRuntimeException(final AppExceptionCode code, final Throwable cause){
		return new AppRuntimeException(code, cause);
	}

	public static AppRuntimeException createAppRuntimeException(final AppExceptionCode code, final String message, final Throwable cause){
		return new AppRuntimeException(code, message, cause);
	}
	
}
