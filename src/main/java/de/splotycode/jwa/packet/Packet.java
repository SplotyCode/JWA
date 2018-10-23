package de.splotycode.jwa.packet;

public interface Packet {

    boolean authNeeded();

    void write(PacketConnection connection);

    default PacketMethod getMethod() {
        return PacketMethod.POST;
    }

    String getPath();

}
