package de.splotycode.jwa.packet.packets;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import de.splotycode.jwa.packet.PacketMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetGroupPacket implements Packet {

    private final String groupId;

    @Override
    public PacketMethod getMethod() {
        return PacketMethod.GET;
    }

    @Override
    public boolean authNeeded() {
        return true;
    }

    @Override
    public void write(PacketConnection connection) {

    }

    @Override
    public String getPath() {
        return "/v1/groups/" + groupId;
    }
}
