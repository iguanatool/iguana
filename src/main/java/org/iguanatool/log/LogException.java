package org.iguanatool.log;

public class LogException extends RuntimeException {

	static final long serialVersionUID = -845729834759832470L;
	
    public LogException(String message) {
        super(message);
    }

    public LogException(Exception e) {
        super(e);
    }

}