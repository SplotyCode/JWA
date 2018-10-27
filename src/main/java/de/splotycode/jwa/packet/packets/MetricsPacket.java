package de.splotycode.jwa.packet.packets;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import de.splotycode.jwa.packet.PacketMethod;

public class MetricsPacket implements Packet {

    @Override
    public boolean authNeeded() {
        return true;
    }

    @Override public void write(PacketConnection connection) {}

    @Override
    public PacketMethod getMethod() {
        return PacketMethod.GET;
    }

    @Override
    public String getPath() {
        return "/metrics?format=prometheus";
    }
}
