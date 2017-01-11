package org.iguanatool.inputgeneration.aim;

public class AimException extends RuntimeException {

    static final long serialVersionUID = -4543339666886838818L;

    public AimException(String message) {
        super(message);
    }

    public AimException(Exception e) {
        super(e);
    }
}
