package com.tmt.common.exception;

import com.tmt.common.exception.BaseRuntimeException;

/**
 * 缓存序列化异常
 * @author lifeng
 */
public class SerializeException extends BaseRuntimeException{
	
	private static final long serialVersionUID = 1L;

	public SerializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializeException(String message) {
		super(message);
	}

	public SerializeException(Throwable cause) {
		super(cause);
	}
}
