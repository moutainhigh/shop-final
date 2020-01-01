package com.tmt.common.exception;

import com.tmt.common.exception.BaseRuntimeException;

/**
 * 无异常
 * 
 * @author lifeng
 */
public class NoSessionException extends BaseRuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NoSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSessionException(String message) {
		super(message);
	}

	public NoSessionException(Throwable cause) {
		super(cause);
	}
}
