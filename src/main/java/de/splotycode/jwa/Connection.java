package de.splotycode.jwa;

import de.splotycode.jwa.core.GatewayStatus;
import de.splotycode.jwa.core.GlobalMultiThreadManager;
import de.splotycode.jwa.core.MultiThreadMode;
import de.splotycode.jwa.core.Status;
import de.splotycode.jwa.listener.ListenerRegistry;
import de.splotycode.jwa.listener.events.StatusChangeEvent;
import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import de.splotycode.jwa.packet.packets.ContactsPacket;
import de.splotycode.jwa.packet.packets.LoginPacket;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import de.splotycode.jwa.response.responses.ContactResponse;
import de.splotycode.jwa.response.responses.HealthResponse;
import de.splotycode.jwa.response.responses.LoginResponse;
import de.splotycode.jwa.response.responses.MetricsResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * The Main Connection to Whatsapp (Not Keep-Alive)
 * @see de.splotycode.jwa.builder.ConnectionBuilder
 */
public class Connection {

    @Getter private SocketAddress address;
    @Setter private String username, password;
    @Getter private String session;
    @Getter private Status status = Status.INITIALISED;
    @Setter private Executor executor = Executors.newSingleThreadExecutor();
    @Getter @Setter private ListenerRegistry listenerRegistry;
    @Getter @Setter private MultiThreadMode threadMode;

    public Connection(SocketAddress address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public void login(boolean async) {
        setStatus(Status.LOGGINGIN);
        Runnable runnable = () -> session = sendPacket(LoginResponse.class, new LoginPacket(username, password)).getToken();
        if (async) {
            execMultiThreaded(runnable);
        } else {
            runnable.run();
        }
        setStatus(Status.ONLINE);
    }

    private void execMultiThreaded(Runnable runnable) {
        switch (threadMode) {
            case NONE:
                throw new IllegalStateException("Can not execute Multithreaded operation if MultiThreadMode is none");
            case GLOBAL:
                GlobalMultiThreadManager.getInstance().exec(runnable);
                break;
            case INSTANCE:
                executor.execute(runnable);
                break;
        }
    }

    public <P extends Response> void sendPacket(Class<P> clazz, Consumer<P> consumer) {
        execMultiThreaded(() -> {
            P p = sendPacket(clazz);
            consumer.accept(p);
        });
    }

    public <P extends Response> P sendPacket(Class<P> clazz) {
        try {
            return sendPacket(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <P extends Response> void sendPacket(Class<P> clazz, Packet packet, Consumer<P> consumer) {
        execMultiThreaded(() -> {
            P p = sendPacket(clazz, packet);
            consumer.accept(p);
        });
    }

    public <P extends Response> P sendPacket(Class<P> clazz, Packet packet) {
        try {
            return sendPacket(clazz.newInstance(), packet);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <P extends Response> void sendPacket(P response, Consumer<P> consumer) {
        execMultiThreaded(() -> {
            P p = sendPacket(response);
            consumer.accept(p);
        });
    }

    private <P extends Response> P sendPacket(P response) {
        return sendPacket(response, response.getPacket());
    }

    public <P extends Response> void sendPacket(P response, Packet packet, Consumer<P> consumer) {
        execMultiThreaded(() -> {
            P p = sendPacket(response, packet);
            consumer.accept(p);
        });
    }

    public <P extends Response> P sendPacket(P response, Packet packet) {
        PacketConnection connection = new PacketConnection(this, packet);
        packet.write(connection);
        try {
            ResponseContext result = connection.send(response);
            response.read(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public synchronized boolean isLoggedIn() {
        return status == Status.ONLINE;
    }

    public void awaitReady() throws InterruptedException {
        while (status != Status.ONLINE) {
            Thread.sleep(150L);
        }
    }

    public synchronized void setStatus(Status status) {
        listenerRegistry.callEvent(new StatusChangeEvent(this.status, status));
        this.status = status;
    }

    public void isNumberValid(String number, Consumer<Boolean> validConsumer) {
        execMultiThreaded(() -> validConsumer.accept(isNumberValid(number)));
    }

    public boolean isNumberValid(String number) {
        return sendPacket(ContactResponse.class, new ContactsPacket(new String[] {number})).getFirstContact().isValid();
    }

    public void getGatewayStatus(Consumer<GatewayStatus> consumer) {
        sendPacket(HealthResponse.class, healthResponse -> consumer.accept(healthResponse.getGatewayStatus()));
    }

    public GatewayStatus getGatewayStatus() {
        return sendPacket(HealthResponse.class).getGatewayStatus();
    }

    public MetricsResponse getMetrics() {
        return sendPacket(MetricsResponse.class);
    }

    public void getMetrics(Consumer<MetricsResponse> consumer) {
        sendPacket(MetricsResponse.class, consumer);
    }

}
