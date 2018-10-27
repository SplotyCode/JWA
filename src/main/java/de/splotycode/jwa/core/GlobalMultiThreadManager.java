package de.splotycode.jwa.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.Executor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalMultiThreadManager {

    @Getter private static final GlobalMultiThreadManager instance = new GlobalMultiThreadManager();

    @Setter private Executor executor;

    public void exec(Runnable runnable) {
        if (executor == null) throw new ExecutorNotSetException("You need to set Executor in GlobalMultiThreadManager in order to use MultiThreadMode#GLOBAL");
        executor.execute(runnable);
    }

    public static class ExecutorNotSetException extends RuntimeException {

        public ExecutorNotSetException() {
        }

        public ExecutorNotSetException(String s) {
            super(s);
        }

        public ExecutorNotSetException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public ExecutorNotSetException(Throwable throwable) {
            super(throwable);
        }

        public ExecutorNotSetException(String s, Throwable throwable, boolean b, boolean b1) {
            super(s, throwable, b, b1);
        }
    }

}
