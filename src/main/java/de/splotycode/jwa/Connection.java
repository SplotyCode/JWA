package de.splotycode.jwa;

import de.splotycode.jwa.core.Status;
import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Connection {

    @Getter private SocketAddress address;
    @Setter private String username, password;
    @Getter private String session;
    private Status status = Status.INITILISED;
    private Executor executor = Executors.newSingleThreadExecutor();

    public Connection(SocketAddress address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public void login() {

    }

    public <P extends Response> P sendPacket(Class<P> clazz) {
        try {
            return sendPacket(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <P extends Response> P sendPacket(P response) {
        Packet packet = response.getPacket();

        PacketConnection connection = new PacketConnection(this, packet);
        packet.write(connection);
        try {
            ResponseContext result = connection.send();
            response.read(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean isLoggedIn() {
        return status == Status.ONLINE;
    }

    public void awaitReady() throws InterruptedException {
        while (status != Status.ONLINE) {
            Thread.sleep(150L);
        }
    }

}
