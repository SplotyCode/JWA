package de.splotycode.jwa.response;

public class BadResponseException extends RuntimeException {

    public BadResponseException() {
    }

    public BadResponseException(String s) {
        super(s);
    }

    public BadResponseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadResponseException(Throwable throwable) {
        super(throwable);
    }

    public BadResponseException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
