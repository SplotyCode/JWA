package de.splotycode.jwa.packet;

import de.splotycode.jwa.Connection;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.Getter;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PacketConnection {

    @Getter private Connection connection;
    private Packet packet;
    @Getter private JSONObject object = new JSONObject();
    @Getter private Map<String, String> headers = new HashMap<>();

    public PacketConnection(Connection connection, Packet packet) {
        this.connection = connection;
        this.packet = packet;
    }

    /**
     * Adds an Http Header to this Packet
     * @param name the name of the header
     * @param value the value of the header
     */
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    /**
     * Adds an jason variable
     * @param name name if the variable
     * @param value value of the variable
     */
    public void addJson(String name, String value) {
        object.put(name, value);
    }

    /**
     * Adds an jason variable
     * @param name name if the variable
     * @param value value of the variable
     */
    public void addJson(String name, int value) {
        object.put(name, value);
    }

    /**
     * Adds an jason variable
     * @param name name if the variable
     * @param value value of the variable
     */
    public void addJson(String name, long value) {
        object.put(name, value);
    }

    /**
     * Sends the Packet to a Server
     * @return the server response
     * @throws IOException Socket IO Exceptions
     */
    public ResponseContext send(Response response) throws IOException {
        headers.put("Content-Type", "application/json");
        //Add Session Id if needed
        if (packet.authNeeded()) {
            if (!connection.isLoggedIn()) throw new IllegalStateException("You need to authenticate in order to send that packet");
            headers.put("Authorization", "Bearer " + connection.getSession());
        }

        //Create Connection
        InetSocketAddress address = (InetSocketAddress) connection.getAddress();
        Socket socket = new Socket(address.getHostName(), address.getPort());
        Writer out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));

        //First line
        out.append(packet.getMethod().name().toUpperCase()).append(" ").append(packet.getPath()).append("\n");

        //Header
        for (Map.Entry<String, String> header : headers.entrySet()) {
            out.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
        }

        //Body
        out.append(object.toString());

        //End Connection
        out.flush();
        socket.close();
        return new ResponseContext(socket.getInputStream(), response);
    }

}
