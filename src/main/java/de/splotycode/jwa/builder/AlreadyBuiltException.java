package de.splotycode.jwa.builder;

public class AlreadyBuiltException extends RuntimeException {

    public AlreadyBuiltException() {
    }

    public AlreadyBuiltException(String s) {
        super(s);
    }

    public AlreadyBuiltException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AlreadyBuiltException(Throwable throwable) {
        super(throwable);
    }

    public AlreadyBuiltException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
