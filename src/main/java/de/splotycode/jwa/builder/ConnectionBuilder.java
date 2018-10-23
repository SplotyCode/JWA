package de.splotycode.jwa.builder;

import de.splotycode.jwa.Connection;
import de.splotycode.jwa.core.Status;
import de.splotycode.jwa.listener.Listener;
import de.splotycode.jwa.listener.ListenerRegistry;
import de.splotycode.jwa.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Builder for building a Connection
 * @see Builder
 * @see Connection
 */
@Getter
@Setter
public class ConnectionBuilder implements Builder<Connection> {

    private String host;
    private int port;
    private String username, password;

    private Set<Listener> listeners = new HashSet<>();

    public Connection build() {
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

        ListenerRegistry listenerRegistry = new ListenerRegistry();
        listeners.forEach(listenerRegistry::addListener);
        connection.setListenerRegistry(listenerRegistry);

        connection.setStatus(Status.INITILISED);

        connection.login();
        return connection;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
