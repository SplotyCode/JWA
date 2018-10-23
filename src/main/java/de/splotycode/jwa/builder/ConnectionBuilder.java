package de.splotycode.jwa.builder;

import de.splotycode.jwa.Connection;
import de.splotycode.jwa.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

@Getter
@Setter
public class ConnectionBuilder implements Builder<Connection> {

    private String host;
    private int port;
    private String username, password;

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
        connection.login();
        return connection;
    }
}
