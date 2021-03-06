package de.splotycode.jwa.builder;

import de.splotycode.jwa.Connection;
import de.splotycode.jwa.core.MultiThreadMode;
import de.splotycode.jwa.core.Status;
import de.splotycode.jwa.listener.Listener;
import de.splotycode.jwa.listener.ListenerRegistry;
import de.splotycode.jwa.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * Builder for building a Connection
 * @see Builder
 * @see Connection
 */
@Getter
public class ConnectionBuilder implements Builder<Connection> {

    private String host;
    private int port;
    private String username, password;

    private Executor executor = null;
    private MultiThreadMode threadMode = MultiThreadMode.NONE;

    private boolean loginAsync = false;

    private Set<Listener> listeners = new HashSet<>();

    private boolean build = false;

    /**
     * Builds the Connection
     * wants that method is called you can not use this object anymore
     * @return the new Connection
     */
    public Connection build() {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        build = true;
        if (StringUtil.isEmpty(host)) {
            host = "localhost";
        }
        if (port <= 0) {
            throw new IllegalArgumentException("Port needs to be an Positive Number");
        }
        if (username == null) {
            username = "admin";
        }
        if (password == null) {
            password = "secret";
        }

        Connection connection =  new Connection(new InetSocketAddress(host, port), username, password);
        connection.setThreadMode(threadMode);

        ListenerRegistry listenerRegistry = new ListenerRegistry();
        listeners.forEach(listenerRegistry::addListener);
        connection.setListenerRegistry(listenerRegistry);

        connection.setStatus(Status.INITIALISED);

        if (executor != null) {
            connection.setExecutor(executor);
        }

        connection.login(loginAsync);
        return connection;
    }

    public void addListener(Listener listener) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        listeners.remove(listener);
    }

    public ConnectionBuilder setHost(String host) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        this.host = host;
        return this;
    }

    public ConnectionBuilder setPort(int port) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        this.port = port;
        return this;
    }

    public ConnectionBuilder setExecutor(Executor executor) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        this.executor = executor;
        return this;
    }

    public ConnectionBuilder setPassword(String password) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        this.password = password;
        return this;
    }

    public ConnectionBuilder setThreadMode(MultiThreadMode threadMode) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        this.threadMode = threadMode;
        return this;
    }

    public ConnectionBuilder setUsername(String username) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        this.username = username;
        return this;
    }

    public ConnectionBuilder setLoginAsync(boolean loginAsync) {
        if (build) throw new AlreadyBuiltException("Builder was already used (build() called");
        this.loginAsync = loginAsync;
        return this;
    }
}
