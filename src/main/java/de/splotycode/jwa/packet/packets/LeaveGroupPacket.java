package de.splotycode.jwa.packet.packets;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaveGroupPacket implements Packet {

    private final String groupId;

    @Override
    public boolean authNeeded() {
        return true;
    }

    @Override
    public void write(PacketConnection connection) {

    }

    @Override
    public String getPath() {
        return "/v1/groups/" + groupId + "/leave";
    }
}
